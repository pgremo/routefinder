(ns routefinder.core
  (:import java.math.RoundingMode))

(defn round
  "round n to s decimal places"
  [s n]
  (.setScale (bigdec n) s RoundingMode/HALF_EVEN))

(defn shuffle-sub
  "shuffle a sub vector"
  ([l]
    (shuffle-sub l 0))
  ([l s]
    (shuffle-sub l s (count l)))
  ([l s e]
    (vec (concat (subvec l 0 s) (shuffle (subvec l s e)) (subvec l e)))))

(defn swap [v i j]
  "swap 2 values of a vector"
  (assoc v j (v i) i (v j)))

(defn rand-in
  "random value within a range"
  ([k] (rand-int k))
  ([k j] (+ k (rand-int (- j k)))))

(defn in?
  "true if seq contains elm"
  [seq elm]
  (some #(= elm %) seq))

