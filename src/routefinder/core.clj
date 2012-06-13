(ns routefinder.core
  (:use [korma.db])
  (:import java.math.RoundingMode))

(defn round
  [s n]
  (.setScale (bigdec n) s RoundingMode/HALF_EVEN))

(defn shuffle-sub
  ([l]
    (shuffle-sub l 0))
  ([l s]
    (shuffle-sub l s (count l)))
  ([l s e]
    (vec (concat (subvec l 0 s) (shuffle (subvec l s e)) (subvec l e)))))

(defn swap [v i j]
  (assoc v j (v i) i (v j)))

(defn rand-in
  ([k] (rand-int k))
  ([k j] (+ k (rand-int (- j k)))))

(defdb eve {:classname "org.h2.Driver"
            :subprotocol "h2"
            :subname "tcp://localhost/eve"
            :user "sa"
            :password ""})
