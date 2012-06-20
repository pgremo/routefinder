(ns routefinder.models.jumps
  (:use korma.core
        clojure.tools.trace
        routefinder.core)
  (:use [clojure.algo.generic.functor :only [fmap]]))

(defentity JUMPS)

(def jumps (delay (->>
                    (select JUMPS)
                    (group-by :FROMSOLARSYSTEMID )
                    (fmap #(reduce (fn [a b] (assoc a (:TOSOLARSYSTEMID b) (:TOSECURITY b))) {} %)))))

(def max-cost (- Double/MAX_VALUE 1000))

(defn any-neighbor
  [k]
  (fmap (constantly 1) (@jumps k)))

(defn highsec-neighbor
  [k]
  (fmap #(if (>= % 0.5) 1 max-cost) (@jumps k)))

(defn only-highsec-neighbor
  [k]
  (filter (comp (partial = 1) val) (highsec-neighbor k)))
