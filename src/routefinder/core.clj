(ns routefinder.core
  (:use [korma.db]))

(set! *warn-on-reflection* true)

(defn round
  [s n]
  (.setScale (bigdec n) s java.math.RoundingMode/HALF_EVEN))

(defn shuffle-sub
  ([l]
    (shuffle-sub l 0))
  ([l s]
    (shuffle-sub l s (count l)))
  ([l s e]
    (vec (concat (subvec l 0 s) (shuffle (subvec l s e)) (subvec l e)))))

(defn swap [v i1 i2]
  (assoc v i2 (v i1) i1 (v i2)))

(defdb eve {:classname "org.h2.Driver"
            :subprotocol "h2"
            :subname "tcp://localhost/eve"
            :user "sa"
            :password ""})
