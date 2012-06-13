(ns routefinder.server
  (:require [noir.server :as server]))

(server/load-views-ns 'routefinder.views)

(defn -main [& m]
  (let [mode (keyword (or (first m) :dev))
        port (Integer. (get (System/getenv) "PORT" "8089"))]
    (server/start port {:mode mode
                        :ns 'routefinder-noir})))

