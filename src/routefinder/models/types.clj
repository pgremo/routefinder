(ns routefinder.models.types
  (:use [clojure.string :only [lower-case]])
  (:use korma.core
        clojure.algo.generic.functor))

(defentity types (table :TYPES ))

(defn by-name
  [k]
  (select types (where {:TYPE [= k]})))

(defn- pivot
  [k]
  (into {} (concat (map (juxt (comp keyword :ATTRIBUTE ) :VALUE ) k) (select-keys (first k) [:TYPE ]))))

(defn ship-by-name
  [k]
  (let [rows (select types (where {:TYPE [= k] :CATEGORY [= "Ship"]}))]
    (if (empty? rows) rows (pivot rows))))

(defn ship-like-name
  [name]
  (map pivot
    (vals (group-by :TYPE (select types
                            (where (and (= :CATEGORY "Ship")
                                     (like (sqlfn lower :TYPE ) (str "%" (lower-case name) "%")))))))))
