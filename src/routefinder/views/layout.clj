(ns routefinder.views.layout
  (:require [net.cgrand.enlive-html :as html])
  (:use noir.core
        clojure.pprint))

(html/deftemplate layout
  "routefinder/views/layout.html"
  [head body]
  [:head ] (html/append head)
  [:div#wrapper ] (html/content body))