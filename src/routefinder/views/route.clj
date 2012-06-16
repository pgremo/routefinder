(ns routefinder.views.route
  (:require [routefinder.views.common :as common]
            [noir.content.getting-started]
            [noir.request :as req])
  (:use routefinder.core
        hiccup.page
        hiccup.form
        noir.request
        routefinder.genetic)
  (:use [clojure.string :only [join]])
  (:use [noir.core :only [defpage defpartial]]))


;  ["Muvolailen" "Amarr" "Jita" "Rens" "Dodixie" "Mani" "Oimmo" "Dabrid"]

(defn route-result
  [nodes]
  (->>
    (solve nodes)
    (drop 25)
    (first)
    (route)
    (map rest)
    (flatten)
    (map (juxt (partial in? nodes) identity))))

(defpartial route-item
  [index [waypoint segment]]
  [:tr.route [:td (inc index)] [:td waypoint] [:td segment]])

(defpartial route-body
  [items]
  [:tbody.route (map-indexed route-item items)])

(defpage [:post "/route"] {:keys [waypoint]}
  (common/layout
    []
    [:table (route-body (route-result waypoint))]))

(defpage [:get "/route"] []
  (common/layout
    [(include-css "/css/themes/base/jquery.ui.all.css")
     (include-css "/css/route.css")
     (include-js "/js/string.js")
     (include-js "/js/jquery-1.7.2.min.js")
     (include-js "/js/ui/jquery.ui.core.js")
     (include-js "/js/ui/jquery.ui.widget.js")
     (include-js "/js/ui/jquery.ui.position.js")
     (include-js "/js/ui/jquery.ui.autocomplete.js")
     (include-js "/js/ui/jquery.ui.button.js")
     (include-js "/js/route.js")]
    (form-to [:post "/route"]
      [:div.ui-widget (label "solarsystem" "Solar System Name")
       (text-field "solarsystem")]
      [:div.ui-widget [:ol#waypoints ]]
      (submit-button "Find Route"))))

