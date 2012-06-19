(ns routefinder.models.solarsystems
  (:use [clojure.string :only [lower-case]])
  (:use korma.core))

(defentity solarsystem (table :MAPSOLARSYSTEMS ))

(defn like-name
  [name]
  (select solarsystem
    (where (like (sqlfn lower :SOLARSYSTEMNAME ) (str "%" (lower-case name) "%")))))

(defn by-name
  [name]
  (select solarsystem
    (where (= (sqlfn lower :SOLARSYSTEMNAME ) (lower-case name)))))
