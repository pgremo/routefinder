(ns routefinder.views.welcome
  (:require [routefinder.views.common :as common]
            [noir.content.getting-started])
  (:use [routefinder.core])
  (:use [routefinder.genetic])
  (:use [clojure.string :only [join]])
  (:use [noir.core :only [defpage defpartial]]))

(defpartial route-item
  [index [waypoint segment]]
  [:tr.route [:td (inc index)] [:td waypoint] [:td segment]])

(def nodes
  ["Muvolailen" "Amarr" "Jita" "Rens" "Dodixie" "Mani" "Oimmo" "Dabrid"])

(def route-result
  (->>
    (solve nodes)
    (drop 25)
    (first)
    (route)
    (map rest)
    (flatten)
    (map (juxt (partial in? nodes) identity))))

(defpartial route-list
  [items]
  [:tbody.route (map-indexed route-item items)])

(defpage "/welcome" []
  (common/layout
    [:table (route-list route-result)]))
