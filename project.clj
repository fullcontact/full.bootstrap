(defproject fullcontact/full.bootstrap "0.10.7-SNAPSHOT"
  :description "Boostrap module that pulls in all commonly used full-monty dependencies."
  :url "https://github.com/fullcontact/full.bootstrap"
  :license {:name "Eclipse Public License - v 1.0"
            :url "http://www.eclipse.org/legal/epl-v10.html"
            :distribution :repo}
  :deploy-repositories [["releases" {:url "https://clojars.org/repo/" :creds :gpg}]]
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [fullcontact/full.core "0.10.1" :exclusions [org.clojure/clojurescript]]
                 [fullcontact/camelsnake "0.9.0"]
                 [fullcontact/full.json "0.10.1"]
                 [fullcontact/full.async "0.9.0"]
                 [fullcontact/full.cache "0.10.1"]
                 [fullcontact/full.http "0.10.7"]]
  :release-tasks [["vcs" "assert-committed"]
                  ["change" "version" "leiningen.release/bump-version" "release"]
                  ["vcs" "commit"]
                  ["vcs" "tag" "--no-sign"]
                  ["deploy"]
                  ["change" "version" "leiningen.release/bump-version"]
                  ["vcs" "commit"]
                  ["vcs" "push"]]
  :aot :all)
