(ns routefinder.a-star
  (:use routefinder.models)
  (:use [clojure.data.priority-map :only [priority-map]])
  (:use [clojure.algo.generic.functor :only [fmap]]))

(defn a-star
  "Performs an a* search over the data using heuristic est-cost.

  ARGUMENTS:
    est-cost(e)  The estimate of the cost to the goal.
    neighbors(e) Returns map of neighbors at e and their costs
    start        The element at which to start
    goal?(e)     Function that checks if the goal has been reached.

  RETURNS:
    The list of elements in the optimal path. "
  [est-cost neighbors start goal?]
  (loop [open (priority-map start [0 nil])
         closed {}]
    (let [[e [s p]] (first open)]
      (cond
        (nil? e) "Path not found! No more elements to try!"
        (goal? e) (->>
                    (conj (iterate (comp second closed) p) e)
                    (take-while (comp not nil?))
                    reverse)
        :else (recur
                (merge-with (partial min-key first)
                  (dissoc open e)
                  (into {} (for [[n ns] (neighbors e)
                                 :when ((comp not closed) n)]
                             [n [(+ ns s (est-cost n)) e]])))
                (assoc closed e [s p]))))))

(defn any-neighbor
  [k]
  (fmap (constantly 1) (jumps k)))

(defn highsec-neighbor
  [k]
  (fmap #(if (>= % 0.5) 1 Integer/MAX_VALUE) (jumps k)))

(defn only-highsec-neighbor
  [k]
  (filter (comp (partial = 1) val) (highsec-neighbor k)))
