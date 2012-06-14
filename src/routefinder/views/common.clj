(ns routefinder.views.common
  (:use [noir.core :only [defpartial]]
        [hiccup.page :only [include-css html5]]))

(defpartial layout [includes & content]
  (html5
    [:head [:title "routefinder"]
     (map identity includes)
     (include-css "/css/reset.css")]
    [:body [:div#wrapper content]]))
