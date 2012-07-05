(ns routefinder.models.characters
  (:use clojure.set
        clojure.data.zip.xml)
  (:require [routefinder.eve :as eve]))

(defn like-name
  [term keys]
  (filter #(re-find (re-pattern (str "(?i)" term)) (:name %)) (flatten (for [[id code] keys] (eve/characters id code)))))

(defn skills
  [characterid keyid vcode]
  (set (map (comp #(into {} (for [[k v] %] [k (Integer/parseInt v)])) #(rename-keys % {:typeID :typeid}) :attrs first) (xml-> (first (eve/character keyid vcode characterid)) :rowset (attr= :name "skills") :row ))))