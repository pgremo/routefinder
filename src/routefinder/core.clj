(ns routefinder.core
  (:use [korma.db])
  (:use [korma.core])
  (:use [clojure.data.priority-map :only [priority-map]]))

(defn a*-search
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
        (goal? e) (->> (conj (iterate #(second (closed %)) p) e)
                    (take-while #(not (nil? %)))
                    reverse)
        :else (recur
                (merge-with #(if (< (first %1) (first %2)) %1 %2)
                  (dissoc open e)
                  (into {} (for [[n ns] (neighbors e)
                                 :when (not (get closed n))]
                             [n [(+ ns s (est-cost n)) e]])))
                (assoc closed e [s p]))))))

(defn map-vals [m f]
  (reduce (fn [altered-map [k v]] (assoc altered-map k (f v))) {} m))

(defdb eve {:classname "org.h2.Driver"
            :subprotocol "h2"
            :subname "tcp://localhost/~/Development/routefinder/eve"
            :user "sa"
            :password ""})

(defentity JUMPS)

(def jumps (map-vals (group-by #(get % :TOSYSTEM) (select JUMPS)) #(reduce (fn [a b](assoc a (get b :FROMSYSTEM) (get b :COST))) {} %)))



