(ns routefinder.views.route
  (:require [routefinder.models.solarsystems :as solarsystems]
            [routefinder.models.gates :as gates])
  (:use net.cgrand.enlive-html
        routefinder.views.layout
        routefinder.core
        routefinder.genetic
        noir.core))

(defn route-finder
  [nodes]
  (->>
    (solve nodes)
    (nth 26)
    (route)
    (map rest)
    (flatten)
    (map (comp (juxt :SOLARSYSTEMID :COST) gates/by-id))
    (map (juxt (partial in? nodes) (comp :SOLARSYSTEMNAME solarsystems/by-id :SOLARSYSTEMID gates/by-id) (comp :COST gates/by-id)))))

(defn route-finder2
  [nodes]
  (let [segments (route (nth 26 (solve nodes)))]
    (for [[first second] (map () segments (rest segments))]
      [])))

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

