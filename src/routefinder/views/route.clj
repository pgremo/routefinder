(ns routefinder.views.route
  (:require [routefinder.models.solarsystem :as solarsystem]
            [routefinder.models.gates :as gates]
            [routefinder.models.types :as types]
            [clojure.set :as set])
  (:use net.cgrand.enlive-html
        routefinder.views.layout
        routefinder.genetic
        routefinder.a-star
        routefinder.core
        clojure.tools.trace
        clojure.algo.generic.functor
        noir.core))

(def agility-skills #{{:typeid 3327} {:typeid 3453}})
(def advanced-spaceship-command #{{:typeid 20342}})

(defn agility-skill-value
  [{:keys [agilityBonus level]}]
  (+ 1.0 (* agilityBonus level 0.01)))

(deftrace calc-align-time
  [ship skills]
  (let [converted (set/join (all-skills) skills)

        agility-bonus (concat agility-skills (if (= (:advancedAgility ship) 1.0) advanced-spaceship-command))

        freighterBonusA2 (:freighterBonusA2 ship)

        feighter (if freighterBonusA2 (join #{(skill-by-id (:requiredSkill1 ))} converted))

        {:keys [agility mass]} ship]

    (* 1e-6 (- (ln 0.25)) agility mass (reduce * (map agility-skill-value (set/join converted agility-bonus))) feighter)))

(defn find-route
  [nodes ship skills]
  (let [warp-speed (* 3.0 (:warpSpeedMultiplier ship))

        align-time (calc-align-time ship skills)

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
  (let [skills #{{:typeid 3453 :level 5} {:typeid 3327 :level 5} {:typeid 20342 :level 4}}]
    (layout (header) (result (find-route (map #(Long/valueOf %) waypoint) (types/ship-by-name ship) skills)))))

(defpage [:get "/route"] []
  (layout (header) (form)))
