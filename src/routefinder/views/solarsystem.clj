(ns routefinder.views.solarsystem
  (:require [routefinder.models.solarsystem :as solarsystem])
  (:use noir.response
        noir.core))

(defpage "/solarsystem/search.json" {:keys [term]}
  (json (map #(assoc {} :label (:SOLARSYSTEMNAME %) :id (:SOLARSYSTEMID %)) (solarsystem/like-name term))))
