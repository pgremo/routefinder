(ns routefinder.views.ship
  (:require [routefinder.models.types :as types]
            [noir.response :as response])
  (:use noir.core))

(defpage "/ship/search.json" {:keys [term]}
  (response/json (map #(assoc {} :label (:typename %) :id (:typeid %)) (types/ship-like-name term))))
