(ns routefinder.views.solarsystem
  (:use routefinder.models.solarsystems
        noir.response
        noir.core))

(defpage "/solarsystem/search.json" {:keys [term]}
  (json (map :SOLARSYSTEMNAME (solarsystems-by-name term))))
