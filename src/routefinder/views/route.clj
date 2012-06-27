(ns routefinder.views.route
  (:require [routefinder.models.solarsystem :as solarsystem]
            [routefinder.models.gates :as gates]
            [routefinder.models.types :as types])
  (:use net.cgrand.enlive-html
        routefinder.views.layout
        routefinder.genetic
        routefinder.a-star
        routefinder.core
        clojure.tools.trace
        clojure.algo.generic.functor
        noir.core))

(def skills '({:type "Evasive Maneuvering" :agilityBonus -5 :level 5} {:type "Spaceship Command" :agilityBonus -2 :level 5} {:type "Advanced Spaceship Command" :agilityBonus -5 :level 4}))

(deftrace agility-skill-value
  [[skill]]
  (+ 1.0 (* (:agilityBonus skill) (:level skill) 0.01)))

(deftrace calc-align-time
  [ship]
  (let [{spaceship-command "Spaceship Command" evasive-maneuvering "Evasive Maneuvering" advanced-spaceship-command "Advanced Spaceship Command" :or {spaceship-command 1.0 evasive-maneuvering 1.0 advanced-spaceship-command 1.0}} (fmap agility-skill-value (group-by :type skills))
        {:keys [agility mass]} ship]
    (* 1e-6 (- (ln 0.25)) agility mass spaceship-command evasive-maneuvering advanced-spaceship-command)))

(defn find-route
  [nodes ship]
  (let [warp-speed (* 3.0 (:warpSpeedMultiplier ship))

        align-time (calc-align-time ship)

        adjust (comp (partial + align-time) (partial / warp-speed))

        adjust-cost (fn [k] (for [[id cost] (gates/only-highsec-neighbor k)] [id (if (not= 1.0 cost) (adjust cost) 1.0)]))

        path (memoize #(a-star (constantly 0) adjust-cost (gates/find-start %1) (partial gates/goal? %2)))

        route (fn [coll] (map #(apply path %) (partition 2 1 coll)))

        fitness (memoize #(reduce + (map (comp dec count) (route %))))

        segments (flatten (route (nth (solve fitness nodes) 26)))]

    (for [{id :DESTINATIONSYSTEMID cost :COST} (map #(apply gates/by-id %) (partition 2 1 segments))]
      [(in? nodes id) (:SOLARSYSTEMNAME (solarsystem/by-id id)) cost])))

(defsnippets "templates/route.html"
  (form [:form ] [])
  (header [:head :> :* ] [])
  (result [:table ] [nodes] [:tr ] (clone-for [[index [waypoint segment cost]] (map-indexed #(vec %&) nodes)]
                                     [[:td (nth-child 1)]] (content (String/valueOf index))
                                     [[:td (nth-child 2)]] (content (String/valueOf waypoint))
                                     [[:td (nth-child 3)]] (content (String/valueOf segment))
                                     [[:td (nth-child 4)]] (content (String/valueOf cost)))))

(defpage [:post "/route"] {:keys [waypoint ship]}
  (layout (header) (result (find-route (map #(Long/valueOf %) waypoint) (types/ship-by-name ship)))))

(defpage [:get "/route"] []
  (layout (header) (form)))
