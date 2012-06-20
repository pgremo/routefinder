(ns routefinder.models.solarsystems
  (:use [clojure.string :only [lower-case]])
  (:use korma.core
        clojure.tools.trace))

(defentity solarsystem (table :MAPSOLARSYSTEMS ))

(defn like-name
  [name]
  (select solarsystem
    (where (like (sqlfn lower :SOLARSYSTEMNAME ) (str "%" (lower-case name) "%")))))

(defn by-id
  [id]
  (first (select solarsystem
    (where {:SOLARSYSTEMID [= id]}))))
