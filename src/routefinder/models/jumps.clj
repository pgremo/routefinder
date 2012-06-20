(ns routefinder.models.jumps
  (:use korma.core
        clojure.tools.trace
        routefinder.core)
  (:use [clojure.algo.generic.functor :only [fmap]]))

(defentity jumps (table :JUMPS ))

(def max-cost (- Double/MAX_VALUE 1000))

(defn by-from-system-id
  [k]
  (map (juxt :TOSOLARSYSTEMID :TOSECURITY )
    (select jumps (where {:FROMSOLARSYSTEMID [= k]}))))

(defn any-neighbor
  [k]
  (map (juxt first (constantly 1)) (by-from-system-id k)))

(defn highsec-neighbor
  [k]
  (for [[id sec] (by-from-system-id k)]
    [id (if (>= sec 0.5) 1 max-cost)]))

(defn only-highsec-neighbor
  [k]
  (filter (comp (partial = 1) second) (highsec-neighbor k)))
