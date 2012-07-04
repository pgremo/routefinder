(ns routefinder.version)

(defn version
  []
  (-> ^Object version (. (getClass)) (. (getPackage)) (. (getImplementationVersion))))
