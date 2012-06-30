(ns routefinder.views.ship
  (:require [routefinder.models.types :as types])
  (:use noir.response noir.core))

(defpage "/ship/search.json" {:keys [term]}
  (json (map #(assoc {} :label (:typename %) :id (:typeid %)) (types/ship-like-name term))))
