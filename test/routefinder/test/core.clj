(ns routefinder.test.core
  (:use [routefinder.core])
  (:use [clojure.test]))

(def demo-graph {:red {:green 10, :blue 5, :orange 8},
                 :green {:red 10,   :blue 3},
                 :blue {:green 3,  :red 5, :purple 7},
                 :purple {:blue 7,   :orange 2},
                 :orange {:purple 2, :red 2}})

(deftest find-distances-from-red
  (is (= {:green 8, :blue 5, :purple 10, :red 0, :orange 8} (dijkstra demo-graph :red ))))
