(ns routefinder.genetic
  (:use clojure.tools.trace
        routefinder.core
        routefinder.models
        routefinder.a_star)
  (:use [clojure.math.numeric-tower :only [abs]])
  (:use [clojure.algo.generic.functor :only [fmap]]))

(set! *warn-on-reflection* true)

(defn any-neighbor
  [k]
  (fmap (constantly 1) (jumps k)))

(defn highsec-neighbor
  [k]
  (fmap #(if (>= % 0.5) 1 Integer/MAX_VALUE) (jumps k)))

(defn only-highsec-neighbor
  [k]
  (filter (comp (partial = 1) val) (highsec-neighbor k)))

(def path-count
  (memoize #(count (a-star (constantly 0) only-highsec-neighbor %1 (partial = %2)))))

(defn distance
  "Determines the fitness of a given subject based on the
  distance between it and the target"
  [subject]
  (reduce + (map path-count subject (drop 1 subject))))

(def fitness
  (memoize distance))

(defn create-initial-sample
  "Creates a subjects of the given length composed of random elements"
  [target]
  (let [target-seq (vec target)]
    (repeatedly 1000 #(shuffle-sub target-seq 1))))

(defn tourny-select-subject
  "Takes a list of subjects, selects two at random, and returns the one with
  best fitness"
  [list]
  (min-key fitness (rand-nth list) (rand-nth list)))

(defn select-subjects
  "The selected group is generated by taking the best 10 subjects (elitism),
  then repeated calling tourny-select on the sample to generate 90 more."
  [sample]
  (concat (take 10 sample) (repeatedly 90 #(tourny-select-subject sample))))

(defn breed
  "Decides whether to breed cleanly or with a mutation. Also provides a random
  position for breeding if not provided"
  [s1]
  (if (< 0 (rand-int 2))
    s1
    (let [size (dec (count s1))]
      (swap s1 (inc (rand-int size)) (inc (rand-int size))))))

(defn breed-subjects
  "Generates the solution set by repeatedly selecting two subjects
  (at random) and breeding them"
  [subjects]
  (map breed (repeatedly 1000 #(rand-nth subjects))))

(defn solve
  "Iterate over generations producing the most fit specimens"
  [target]
  (->>
    (create-initial-sample target)
    (iterate (comp (partial sort-by fitness) breed-subjects select-subjects))
    (map first)))

(defn -main
  []
  (time
    (println (->>
               (solve ["Muvolailen" "Amarr" "Jita" "Rens" "Dodixie" "Mani" "Oimmo" "Dabrid"])
               (drop 25)
               (map (juxt fitness identity))
               (first)))))