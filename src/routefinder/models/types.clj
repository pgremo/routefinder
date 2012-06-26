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
  (map #(into {} (concat (map (juxt :ATTRIBUTE :VALUE ) %) {:TYPE (:TYPE (first %))}))
    (select types (where {:TYPE [= k] :CATEGORY [= "Ship"]}))))

(defn ship-like-name
  [name]
  (map #(into {} (concat (map (juxt :ATTRIBUTE :VALUE ) %) {:TYPE (:TYPE (first %))}))
    (vals (group-by :TYPE (select types
                            (where (and (= :CATEGORY "Ship")
                                     (like (sqlfn lower :TYPE ) (str "%" (lower-case name) "%")))))))))

