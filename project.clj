(defproject routefinder/routefinder "1.0.0-SNAPSHOT"
  :dependencies [[org.clojure/algo.generic "0.1.0"]
                 [org.clojure/clojure "1.4.0"]
                 [org.clojure/data.priority-map "0.0.1"]
                 [org.clojure/math.numeric-tower "0.0.1"]
                 [org.clojars.ogrim/korma "0.3.0-beta10"]
                 [ring
                  "1.1.0"
                  :exclusions [org.clojure/clojure clj-stacktrace]]
                 [net.cgrand/moustache "1.1.0"]
                 [clj-yaml "0.3.1"]]
  :profiles {:dev {:dependencies [[org.clojure/tools.trace "0.7.3"]
                                  [com.h2database/h2 "1.3.167"]]}}
  :main routefinder.genetic
  :min-lein-version "2.0.0"
  :warn-on-reflection true
  :description "find shortest route between an arbitrary set of points")