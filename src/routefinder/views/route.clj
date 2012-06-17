(ns routefinder.views.route
  (:use net.cgrand.enlive-html
        routefinder.views.layout
        routefinder.core
        routefinder.genetic
        noir.core))

(defn route-finder
  [nodes]
  (->>
    (solve nodes)
    (drop 25)
    (first)
    (route)
    (map rest)
    (flatten)
    (map (juxt (partial in? nodes) identity))))

(defsnippets "templates/route.html"
  (form [:form ] [])
  (header [:head :> :* ] [])
  (result [:table ] [nodes] [:tr ] (clone-for [[index [waypoint segment]] (map-indexed #(vec %&) nodes)]
                                     [[:td (nth-child 1)]] (content (String/valueOf index))
                                     [[:td (nth-child 2)]] (content (String/valueOf waypoint))
                                     [[:td (nth-child 3)]] (content segment))))

(defpage [:post "/route"] {:keys [waypoint]}
  (layout (header) (result (route-finder waypoint))))

(defpage [:get "/route"] []
  (layout (header) (form)))

