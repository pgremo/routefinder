(ns routefinder.models.jumps
  (:use korma.core
        clojure.tools.trace))

(defentity jumps (table :JUMPS ))

(def max-cost (- Double/MAX_VALUE 1000))

(defn find-start
  [k]
  k)

(defn goal?
  [j k]
  (= j k))

(defn any-neighbor
  [k]
  (map
    (juxt :TOSOLARSYSTEMID (constantly 1))
    (select jumps (where {:FROMSOLARSYSTEMID [= k]}))))

(defn highsec-neighbor
  [k]
  (for [{id :TOSOLARSYSTEMID sec :TOSECURITY} (select jumps (where {:FROMSOLARSYSTEMID [= k]}))]
    [id (if (>= sec 0.5) 1 max-cost)]))

(defn only-highsec-neighbor
  [k]
  (map
    (juxt :TOSOLARSYSTEMID (constantly 1))
    (select jumps
      (where {:FROMSOLARSYSTEMID [= k]
              :TOSECURITY [>= 0.5]}))))
