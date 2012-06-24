(ns routefinder.views.route
  (:require [routefinder.models.solarsystems :as solarsystems]
            [routefinder.models.gates :as gates]
            [routefinder.core :as core])
  (:use net.cgrand.enlive-html
        routefinder.views.layout
        routefinder.genetic
        routefinder.a-star
        clojure.tools.trace
        noir.core))


(def path
  (memoize #(a-star (constantly 0) gates/only-highsec-neighbor (gates/find-start %1) (partial gates/goal? %2))))

(defn route
  [coll]
  (map #(apply path %) (partition 2 1 coll)))

(def fitness
  (memoize #(reduce + (map (comp dec count) (route %)))))

(defn route-finder
  [nodes]
  (let [[segments] (route (nth (solve fitness nodes) 26))]
    (for [{id :DESTINATIONSYSTEMID cost :COST} (map #(apply gates/by-id %) (partition 2 1 segments))]
      [(core/in? nodes id) (:SOLARSYSTEMNAME (solarsystems/by-id id)) cost])))

(defsnippets "templates/route.html"
  (form [:form ] [])
  (header [:head :> :* ] [])
  (result [:table ] [nodes] [:tr ] (clone-for [[index [waypoint segment cost]] (map-indexed #(vec %&) nodes)]
                                     [[:td (nth-child 1)]] (content (String/valueOf index))
                                     [[:td (nth-child 2)]] (content (String/valueOf waypoint))
                                     [[:td (nth-child 3)]] (content (String/valueOf segment))
                                     [[:td (nth-child 4)]] (content (String/valueOf cost)))))

(defpage [:post "/route"] {:keys [waypoint]}
  (layout (header) (result (route-finder (map #(Long/valueOf %) waypoint)))))

(defpage [:get "/route"] []
  (layout (header) (form)))

; warp speed = 3 * warpSpeedMultiplier

