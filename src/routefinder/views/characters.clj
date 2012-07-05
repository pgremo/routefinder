(ns routefinder.views.characters
  (:require [routefinder.models.characters :as characters]
            [noir.response :as response])
  (:use noir.core
        [clojure.string :only [split]]))

(defpage "/character/search.json" {:keys [term apiKeys]}
  (response/json (map #(assoc {} :label (:name %) :id (:characterID %) :keyID (:keyID %) :vCode (:vCode %)) (characters/like-name term (map #(split % #":") apiKeys)))))
