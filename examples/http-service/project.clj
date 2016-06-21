(defproject example "0.1.0-SNAPSHOT"
  :description "Example full.bootstrap HTTP service"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [fullcontact/full.bootstrap "0.10.1"]]
  :main example.api
  :plugins [[lein-midje "3.1.3"]]
  :source-paths ["src"]
  :profiles {:dev {:dependencies [[midje "1.7.0"]]}})
