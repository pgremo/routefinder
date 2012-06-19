(ns routefinder.views.solarsystem
  (:require [routefinder.models.solarsystems :as solarsystems])
  (:use noir.response
        noir.core))

(defpage "/solarsystem/search.json" {:keys [term]}
  (json (map :SOLARSYSTEMNAME (solarsystems/like-name term))))
