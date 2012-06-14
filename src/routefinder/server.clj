(ns routefinder.server
  (:use [korma.db])
  (:require [noir.server :as server]))

(defdb eve {:classname "org.h2.Driver"
            :subprotocol "h2"
            :subname "tcp://localhost/eve;SCHEMA_SEARCH_PATH=INFORMATION_SCHEMA,PUBLIC,STATIC"
            :user "sa"
            :password ""})

(server/load-views-ns 'routefinder.views)

(defn -main [& m]
  (let [mode (keyword (or (first m) :dev ))
        port (Integer. (get (System/getenv) "PORT" "8089"))]
    (server/start port {:mode mode
                        :ns 'routefinder})))

