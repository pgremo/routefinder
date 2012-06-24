(ns routefinder.genetic
  (:use routefinder.core))

(defn solve
  "Iterate over generations producing the most fit specimens"
  [fitness proto]
  (letfn [(create-initial-sample [items]
            (repeatedly 1000 #(shuffle-sub (vec items) 1)))
          (breed [k]
            (if (< 0 (rand-int 2)) k (apply swap k (repeatedly 2 #(rand-in 1 (count k))))))
          (breed-subjects [coll]
            (map breed (repeatedly 1000 #(rand-nth coll))))
          (select [items]
            (apply min-key fitness (repeatedly 2 #(rand-nth items))))
          (select-subjects [items]
            (concat (take 10 items) (repeatedly 90 #(select items))))]
    (->>
      (create-initial-sample proto)
      (iterate (comp (partial sort-by fitness) breed-subjects select-subjects))
      (map first))))
