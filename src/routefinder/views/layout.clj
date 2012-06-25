(ns routefinder.views.layout
  (:use net.cgrand.enlive-html
        routefinder.version
        noir.core))

(deftemplate layout
  "templates/layout.html"
  [head body]
  [:head ] (append head)
  [:div#wrapper ] (content body)
  [:div#footer ] (content (String/valueOf (version))))