(ns routefinder.server
  (:use korma.db
        ring.middleware.json-params)
  (:require [noir.server :as server])
  (:gen-class ))

(defdb eve {:classname "org.h2.Driver"
            :subprotocol "h2"
            :subname "tcp://localhost/eve;SCHEMA_SEARCH_PATH=INFORMATION_SCHEMA,PUBLIC,STATIC"
            :user "sa"
            :password ""})

(server/load-views-ns 'routefinder.views)
(server/add-middleware wrap-json-params)

(defn -main [& m]
  (let [mode (keyword (or (first m) :dev ))
        port (Integer/parseInt (get (System/getenv) "PORT" "8089"))]
    (server/start port {:mode mode
                        :ns 'routefinder})))

