(ns routefinder.views.solarsystems
  (:use routefinder.models.solarsystems
        korma.core)
  (:require [noir.content.getting-started])
  (:use [noir.core :only [defpage]])
  (:use [noir.response :only [json]]))

(defpage "/solarsystems/search.json/:query" {:keys [query]}
  (json (map :SOLARSYSTEMNAME (solarsystems-by-name query))))
