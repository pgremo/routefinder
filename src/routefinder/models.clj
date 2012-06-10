(ns routefinder.models
  (:use korma.core
        routefinder.core)
  (:use [clojure.algo.generic.functor :only [fmap]]))

(defentity JUMPS)

(def jumps (->>
             (select JUMPS)
             (group-by :FROMSYSTEM )
             (fmap #(reduce (fn [a b] (assoc a (:TOSYSTEM b) (round 1 (:TOSECURITY b)))) {} %))))
