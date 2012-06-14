(ns routefinder.views.solarsystems
  (:require [noir.content.getting-started])
  (:use [noir.core :only [defpage]]))

(defpage "/solarsystems/search/:query" {:keys [query]}
  (str "You are seraching for " query))
