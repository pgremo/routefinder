(ns routefinder.test.core
  (:use [routefinder.core])
  (:use [clojure.test])
  (:use [clojure.tools.trace])
  (:use [clojure.algo.generic.functor])
  (:use [clojure.data.priority-map :only [priority-map]]))

(def demo-graph {:red {:green 10, :blue 5, :orange 8},
                 :green {:red 10,   :blue 3},
                 :blue {:green 3,  :red 5, :purple 7},
                 :purple {:blue 7,   :orange 2},
                 :orange {:purple 2, :red 2}})

(deftest find-distances-from-red-a*-search
  (is (= '(:red :blue :green ) (a*-search (fn [a] 0) #(get demo-graph %) :red #(= :green %)))))

(defn any-neighbor
  [k]
  (zipmap (keys (jumps k)) (repeat 1)))

(defn highsec-neighbor
  [k]
  (fmap #(if (>= % 0.5) 1 Integer/MAX_VALUE) (jumps k)))

(defn only-highsec-neighbor
  [k]
  (zipmap (keys (filter #(>= (val %) 0.5) (jumps k))) (repeat 1)))

(prn (a*-search (fn [a] 0) highsec-neighbor "Nonni" #(= "Jita" %)))
