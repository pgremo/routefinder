(ns routefinder.genetic
  (:use clojure.tools.trace
        routefinder.core
        routefinder.models.jumps
        routefinder.a-star)
  (:use [clojure.string :only [join]])
  (:use [clojure.math.numeric-tower :only [abs]])
  (:use [clojure.algo.generic.functor :only [fmap]]))

(def path
  (memoize #(a-star (constantly 0) only-highsec-neighbor %1 (partial = %2))))

(defn route
  [coll]
  (map path coll (drop 1 coll)))

(defn distance
  "Determines the fitness of a given subject based on the
  distance between it and the target"
  [coll]
  (reduce + (map (comp dec count) (route coll))))

(def fitness
  (memoize distance))

(defn create-initial-sample
  "Creates a subjects of the given length composed of random elements"
  [coll]
  (let [target-seq (vec coll)]
    (repeatedly 1000 #(shuffle-sub target-seq 1))))

(defn tourny-select-subject
  "Takes a list of subjects, selects two at random, and returns the one with
  best fitness"
  [coll]
  (min-key fitness (rand-nth coll) (rand-nth coll)))

(defn select-subjects
  "The selected group is generated by taking the best 10 subjects (elitism),
  then repeated calling tourny-select on the sample to generate 90 more."
  [coll]
  (concat (take 10 coll) (repeatedly 90 #(tourny-select-subject coll))))

(defn breed
  "Swap two random elements after the first"
  [k]
  (if (< 0 (rand-int 2))
    k
    (let [size (count k)]
      (swap k (rand-in 1 size) (rand-in 1 size)))))

(defn breed-subjects
  "Generates the solution set by repeatedly selecting two subjects
  (at random) and breeding them"
  [coll]
  (map breed (repeatedly 1000 #(rand-nth coll))))

(defn solve
  "Iterate over generations producing the most fit specimens"
  [coll]
  (->>
    (create-initial-sample coll)
    (iterate (comp (partial sort-by fitness) breed-subjects select-subjects))
    (map first)))
