(ns routefinder.eve
  (:require [clojure.xml :as xml]
            [clojure.zip :as zip]
            [clojure.core.cache :as cache]
            [clj-http.client :as client])
  (:use [clojure.data.zip.xml :only (attr text xml-> xml1->)]))

(defn cache
  {:static true}
  [f t]
  (let [mem (atom (cache/ttl-cache-factory {} :ttl t))]
    (fn [& args]
      (if-let [e (find @mem args)]
        (val e)
        (let [ret (apply f args)]
          (swap! mem assoc args ret)
          ret)))))

(defn zip-str
  [s]
  (zip/xml-zip (xml/parse (java.io.ByteArrayInputStream. (.getBytes s)))))

(def characters
  (cache
    (fn
      [keyid vcode]
      (map (comp #(assoc % :keyID keyid :vCode vcode) :attrs first) (xml-> (zip-str (:body (client/get "https://api.eveonline.com/account/Characters.xml.aspx" {:query-params {"keyID" keyid "vCode" vcode}}))) :result :rowset :row )))
    60000))

(def character
  (cache
    (fn
      [keyid vcode characterid]
      (xml-> (zip-str (:body (client/get "https://api.eveonline.com/char/CharacterSheet.xml.aspx" {:query-params {"keyID" keyid "vCode" vcode "characterID" characterid}}))) :result ))
    60000))