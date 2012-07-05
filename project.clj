(defproject routefinder/routefinder "1.0.0-SNAPSHOT"
  :dependencies [[clj-http "0.4.3"]
                 [com.h2database/h2 "1.3.167"]
                 [enlive "1.0.1"]
                 [korma "0.3.0-beta10"]
                 [log4j "1.2.17" :exclusions [javax.mail/mail
                                              javax.jms/jms
                                              com.sun.jdmk/jmxtools
                                              com.sun.jmx/jmxri]]
                 [noir "1.3.0-beta8"]
                 [org.clojure/algo.generic "0.1.0"]
                 [org.clojure/clojure "1.5.0-alpha1"]
                 [org.clojure/core.cache "0.6.0"]
                 [org.clojure/data.priority-map "0.0.1"]
                 [org.clojure/data.zip "0.1.1"]
                 [org.clojure/math.numeric-tower "0.0.1"]
                 [ring-json-params "0.1.3"]]
  :profiles {:dev {:dependencies [[org.clojure/tools.trace "0.7.3"]]}}
  :jvm-opts ["-Xmx1g"]
  :main routefinder.server
  :min-lein-version "2.0.0"
  :warn-on-reflection true
  :manifest {"Specification-Version" ~#(:version %)
             "Implementation-Version" ~#(format "%s+build.%s" (:version %) (System/getenv "build.number"))}
  :description "find shortest route between an arbitrary set of points")