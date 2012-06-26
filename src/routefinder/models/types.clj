(ns routefinder.models.types
  (:use [clojure.string :only [lower-case]])
  (:use korma.core
        clojure.algo.generic.functor))

(defentity types (table :TYPES ))

(defn by-name
  [k]
  (select types (where {:TYPE [= k]})))

(defn ship-by-name
  [k]
  (let [rows (select types (where {:TYPE [= k] :CATEGORY [= "Ship"]}))]
    (if (empty? rows) rows
      (into {} (concat (map (juxt (comp keyword :ATTRIBUTE ) :VALUE ) rows) {:TYPE (:TYPE (first rows))})))))

(defn ship-like-name
  [name]
  (map #(into {} (concat (map (juxt (comp keyword :ATTRIBUTE ) :VALUE ) %) {:TYPE (:TYPE (first %))}))
    (vals (group-by :TYPE (select types
                            (where (and (= :CATEGORY "Ship")
                                     (like (sqlfn lower :TYPE ) (str "%" (lower-case name) "%")))))))))

