(ns routefinder.models.solarsystems
  (:use [clojure.string :only [lower-case]])
  (:use korma.core))

(defentity solarsystem (table :MAPSOLARSYSTEMS ))

(defn solarsystems-by-name
  [name]
  (select solarsystem
    (where (like (sqlfn lower :SOLARSYSTEMNAME ) (str "%" (lower-case name) "%")))))
