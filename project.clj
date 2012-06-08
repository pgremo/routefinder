(defproject routefinder "1.0.0-SNAPSHOT"
  :description "find shortest route between 2 points"
  :dependencies [[org.clojure/algo.generic "0.1.0"]
                 [org.clojure/clojure "1.4.0"]
                 [org.clojure/data.priority-map "0.0.1"]
                 [org.clojure/math.numeric-tower "0.0.1"]
                 [org.clojars.ogrim/korma "0.3.0-beta10"]]
  :dev-dependencies [[org.clojure/tools.trace "0.7.3"]
                     [com.h2database/h2 "1.3.166"]]
  :plugins [[lein-pprint "1.1.1"]]
  :main routefinder.genetic)