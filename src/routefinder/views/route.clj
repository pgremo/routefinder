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

(html/defsnippets "templates/route.html"
  (form [:form ] [])
  (header [:head :> :* ] [])
  (result [:table ] [nodes] [:tr ] (html/clone-for [[index [waypoint segment]] (map-indexed #(vec %&) nodes)]
                                     [[:td (html/nth-child 1)]] (html/content (String/valueOf index))
                                     [[:td (html/nth-child 2)]] (html/content (String/valueOf waypoint))
                                     [[:td (html/nth-child 3)]] (html/content segment))))

(defpage [:post "/route"] {:keys [waypoint]}
  (layout (header) (result (route-finder waypoint))))

(defpage [:get "/route"] []
  (layout (header) (form)))

