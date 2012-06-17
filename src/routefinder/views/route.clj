(ns routefinder.views.route
  (:require [routefinder.views.common :as common]
            [noir.content.getting-started]
            [noir.request :as req]
            [net.cgrand.enlive-html :as html])
  (:use routefinder.core
        routefinder.views.layout
        hiccup.form
        noir.request
        routefinder.genetic)
  (:use [clojure.string :only [join]])
  (:use [noir.core :only [defpage defpartial]]))


;  ["Muvolailen" "Amarr" "Jita" "Rens" "Dodixie" "Mani" "Oimmo" "Dabrid"]

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

(html/defsnippet route-result
  "routefinder/views/route.html" [:table ]
  [nodes]

  [:tr ] (html/clone-for [[index [waypoint segment]] (map-indexed #(vec %&) nodes)]
           [[:td (html/nth-child 1)]] (html/content (String/valueOf index))
           [[:td (html/nth-child 2)]] (html/content (String/valueOf waypoint))
           [[:td (html/nth-child 3)]] (html/content segment)))

(html/defsnippets "routefinder/views/route.html"
  (route-form [:form ] [])
  (route-form-header [:head :> :* ] []))

(defpage [:post "/route"] {:keys [waypoint]}
  (layout (route-form-header) (route-result (route-finder waypoint))))

(defpage [:get "/route"] []
  (layout (route-form-header) (route-form)))

