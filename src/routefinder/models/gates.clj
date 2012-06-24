(ns routefinder.models.gates
  (:use korma.core clojure.tools.trace))

(defentity gates (table :GATES ))

(def max-cost (- Double/MAX_VALUE 1000))

(defn by-id
  [j k]
  (first
    (select gates (where {:SOURCEID [= j] :DESTINATIONID [= k]}))))

(defn find-start
  [k]
  ((comp :SOURCEID first)
    (select gates (where {:SOURCESYSTEMID [= k]}))))

(defn goal?
  [j k]
  (not-empty
    (select gates (where {:SOURCESYSTEMID [= j] :SOURCEID [= k]}))))

(defn any-neighbor
  [k]
  (map
    (juxt :DESTINATIONID :COST )
    (select gates (where {:SOURCEID [= k]}))))

(defn highsec-neighbor
  [k]
  (for [{id :DESTINATIONID sec :SECURITY cost :COST} (select gates (where {:SOURCEID [= k]}))]
    [id (if (>= sec 0.5) 1 (+ max-cost cost))]))

(defn only-highsec-neighbor
  "take default cost and divide by warp speed (0.75 for freighter) then add time to align (including skills)"
  [k]
  (let [align 38.261724366908981079831213104493
        warpspeed 0.75]
    (map
      (juxt :DESTINATIONID :COST )
      (select gates (where {:SOURCEID [= k] :SECURITY [>= 0.5]})))))
