(ns routefinder.core
  (:use [korma.core])
  (:use [korma.db])
  (:use [clojure.algo.generic.functor :only [fmap]])
  (:use [clojure.data.priority-map :only [priority-map]])
  (:use [clojure.tools.trace]))

(set! *warn-on-reflection* true)

(defn round
  [s n]
  (.setScale (bigdec n) s java.math.RoundingMode/HALF_EVEN))

(defn swap [v i1 i2]
  (assoc v i2 (v i1) i1 (v i2)))

(defdb eve {:classname "org.h2.Driver"
            :subprotocol "h2"
            :subname "tcp://localhost/eve"
            :user "sa"
            :password ""})

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


