(ns routefinder.views.solarsystem
  (:require [routefinder.models.solarsystem :as solarsystem]
            [noir.response :as response])
  (:use noir.core))

(defpage "/solarsystem/search.json" {:keys [term]}
  (response/json (map #(assoc {} :label (:SOLARSYSTEMNAME %) :id (:SOLARSYSTEMID %)) (solarsystem/like-name term))))
