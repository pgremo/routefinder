(ns routefinder.models.jumps
  (:use korma.core
        clojure.tools.trace
        routefinder.core)
  (:use [clojure.algo.generic.functor :only [fmap]]))

(defentity jumps (table :JUMPS ))

(def max-cost (- Double/MAX_VALUE 1000))

(defn by-from-system-id
  [k]
  (reduce #(assoc %1 (:TOSOLARSYSTEMID %2) (:TOSECURITY %2))
    {}
    (select jumps
      (where {:FROMSOLARSYSTEMID [= k]}))))

(defn any-neighbor
  [k]
  (fmap (constantly 1) (by-from-system-id k)))

(defn highsec-neighbor
  [k]
  (fmap #(if (>= % 0.5) 1 max-cost) (by-from-system-id k)))

(defn only-highsec-neighbor
  [k]
  (filter (comp (partial = 1) val) (highsec-neighbor k)))
