(ns routefinder.test.a-star
  (:use [routefinder.core])
  (:use [routefinder.models.jumps])
  (:use [routefinder.a-star])
  (:use [clojure.test]))

(def demo-graph {:red {:green 10, :blue 5, :orange 8},
                 :green {:red 10,   :blue 3},
                 :blue {:green 3,  :red 5, :purple 7},
                 :purple {:blue 7,   :orange 2},
                 :orange {:purple 2, :red 2}})

(deftest find-distances-from-red-a*-search
  (is (= '(:red :blue :green ) (a-star (fn [a] 0) #(get demo-graph %) :red #(= :green %)))))

(prn (a-star (fn [a] 0) only-highsec-neighbor "Nonni" #(= "Jita" %)))
