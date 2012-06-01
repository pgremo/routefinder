(defproject routefinder "1.0.0-SNAPSHOT"
  :description "find shortest route between 2 points"
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [lobos "0.8.0"]
                 [org.clojure/data.priority-map "0.0.1"]
                 [org.clojars.ogrim/korma "0.3.0-beta10"]
                 [com.h2database/h2 "1.3.166"]]
  :profiles {:dev {:dependencies [[org.clojure/tools.trace "0.7.3"]]}})