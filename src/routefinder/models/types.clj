(ns routefinder.models.types
  (:use [clojure.string :only [lower-case]])
  (:use korma.core
        routefinder.core
        clojure.algo.generic.functor))

(defentity types (table :TYPES ))

(defn- pivot
  [k]
  (into {} (concat (map (juxt (comp keyword :ATTRIBUTENAME ) :VALUE ) k) (map-keys (comp keyword lower-case name) (dissoc (first k) :ATTRIBUTENAME :VALUE )))))

(defn by-id
  [k]
  (let [rows (select types (where {:TYPEID [= k]}))]
    (if (empty? rows) nil (pivot rows))))

(defn by-group
  [k]
  (map pivot (vals (group-by :TYPEID (select types (where {:GROUPNAME [= k]}))))))

(defn ship-by-name
  [k]
  (let [rows (select types (where {:TYPENAME [= k] :CATEGORYNAME [= "Ship"]}))]
    (if (empty? rows) nil (pivot rows))))

(defn ship-like-name
  [name]
  (map pivot
    (vals (group-by :TYPEID (select types
                              (where (and (= :CATEGORYNAME "Ship")
                                       (like (sqlfn lower :TYPENAME ) (str "%" (lower-case name) "%")))))))))

(defn all-skills
  []
  (map pivot (vals (group-by :TYPEID (select types (where {:CATEGORYNAME [= "Skills"]}))))))

(defn skill-by-name
  [k]
  (let [rows (select types (where {:TYPENAME [= k] :CATEGORYNAME [= "Skill"]}))]
    (if (empty? rows) nil (pivot rows))))

