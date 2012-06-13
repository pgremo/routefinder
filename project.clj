(defproject routefinder/routefinder "1.0.0-SNAPSHOT"
  :dependencies [[org.clojure/algo.generic "0.1.0"]
                 [org.clojure/clojure "1.4.0"]
                 [org.clojure/data.priority-map "0.0.1"]
                 [org.clojure/math.numeric-tower "0.0.1"]
                 [korma "0.3.0-beta10"]
                 [noir "1.3.0-beta3"]
                 [log4j "1.2.17" :exclusions [javax.mail/mail
                                              javax.jms/jms
                                              com.sun.jdmk/jmxtools
                                              com.sun.jmx/jmxri]]]
  :profiles {:dev {:dependencies [[org.clojure/tools.trace "0.7.3"]
                                  [com.h2database/h2 "1.3.167"]]}}
  :main routefinder.server
  :min-lein-version "2.0.0"
  :warn-on-reflection true
  :description "find shortest route between an arbitrary set of points")