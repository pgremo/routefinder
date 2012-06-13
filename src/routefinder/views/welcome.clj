(ns routefinder.views.welcome
  (:require [routefinder.views.common :as common]
            [noir.content.getting-started])
  (:use [routefinder.genetic])
  (:use [clojure.string :only [join]])
  (:use [noir.core :only [defpage defpartial]]))

(defpartial route-item
  [[length segment]]
  [:li.route length segment])

(def route-result (->>
             (solve ["Muvolailen" "Amarr" "Jita" "Rens" "Dodixie" "Mani" "Oimmo" "Dabrid"])
             (drop 25)
             (first)
             (route)
             (map (juxt fitness identity))))

(defpartial route-list
  [items]
  [:ul.route (map route-item items)])

(defpage "/welcome" []
  (common/layout
    [:p (route-list route-result)]))
