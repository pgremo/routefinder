(ns routefinder.model
  (:use korma.core
        routefinder.core)
  (:use [clojure.algo.generic.functor :only [fmap]]))

(defentity JUMPS)

(def jumps (->>
             (select JUMPS)
             (group-by :FROMSYSTEM )
             (fmap #(reduce (fn [a b] (assoc a (:TOSYSTEM b) (round 1 (:TOSECURITY b)))) {} %))))

(defn any-neighbor
  [k]
  (fmap (constantly 1) (jumps k)))

(defn highsec-neighbor
  [k]
  (fmap #(if (>= % 0.5) 1 Integer/MAX_VALUE) (jumps k)))

(defn only-highsec-neighbor
  [k]
  (filter (comp (partial = 1) val) (highsec-neighbor k)))
