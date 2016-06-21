(ns example.api
 "Example HTTP app using full.boostrap dependencies

   Contains 2 endpoints:

   GET /hello/:username - returns greeting for the username
   GET /github/:username - returns github user info for the given username"
  (:require [full.http.server :as serv]
            [full.core.log :as log]
            [full.core.config :as conf]
            [full.async :refer [<? go-try]]
            [full.http.client :refer [req>]]
            [full.json :refer [read-json]]
            [full.core.dev :refer [start-nstracker]]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [ring.middleware.params :refer [wrap-params]])
  (:gen-class))


(def request-logger (org.slf4j.LoggerFactory/getLogger "example.request"))

(def port (conf/opt :port :default 8080))

(defn github-info> [username]
  (go-try
    (try
      ; In case the request fails with a 404, return nil.
      (<? (req> {:base-url "https://api.github.com" :resource (str "users/" username)}))
      (catch Exception _))))

(defn format-github-user
  "Returns a subset of GitHub data."
  [api-response]
  (log/info api-response)
  (select-keys api-response [:name :company :blog]))


(serv/defroutes app-routes
  ; Returns hello
  (serv/GET "/hello/:username"
    {:keys [params]} {:body (str "Hello, " (:username params)) :status 200})

  ; Returns a subset of GitHub user info
  (serv/GET "/github/:username" {:keys [params]}
    (if-let [user-info (some-> (:username params) github-info> <? format-github-user)]
      {:status 200 :body user-info}
      {:status 404 :body {:message "user not found"}})))


(defn api
  "API middleware"
  [routes]
  (-> routes
      (serv/json-response>)
      (serv/log-track-request> :logger request-logger)
      wrap-keyword-params
      wrap-params))


(defn -main [& _]
  (conf/configure)
  (log/configure)
  (start-nstracker)
  (log/info "Running on" @port)
  (serv/run-server (api #'app-routes) {:port @port :max-line 8193}))
