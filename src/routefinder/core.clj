(ns routefinder.core)

(defn dijkstra [g src]
  (loop [dsts (assoc (zipmap (keys g) (repeat nil)) src 0)
         curr src
         unvi (apply hash-set (keys g))]
    (if (empty? unvi)
      dsts
      (let [unvi (disj unvi curr)
            nextn (first (sort-by #(% dsts) unvi))
            nrds (zipmap (keys g) (map #(select-keys % unvi) (vals g)))]
        (if (empty? (curr nrds))
          (recur dsts nextn unvi)
          (let [cdst (curr dsts)
                roads (select-keys (curr g) unvi)
                reslt (zipmap (keys dsts)
              (map #(if-let [rd (% roads)]
                      (let [idst (% dsts)
                            sum (+ cdst (% roads))]
                        (if (or (nil? idst)
                              (< sum idst))
                          sum idst))
                      (% dsts)) (keys dsts)))]
            (recur reslt nextn unvi)))))))

