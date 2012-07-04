(ns routefinder.views.api-keys
  (:use net.cgrand.enlive-html
        noir.core
        routefinder.views.layout))

(defsnippets "templates/api-keys.html"
  (form [:form ] [])
  (header [:head :> :* ] []))

(defpage [:get "/api-keys"] []
  (layout (header) (form)))