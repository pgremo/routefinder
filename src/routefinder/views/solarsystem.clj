(ns routefinder.views.solarsystem
  (:use routefinder.models.solarsystems
        noir.core)
  (:require [routefinder.views.common :as common]
            [noir.content.getting-started]
            [noir.response :as response]))

(defpage "/solarsystem/search.json" {:keys [term]}
  (response/json (map :SOLARSYSTEMNAME (solarsystems-by-name term))))
