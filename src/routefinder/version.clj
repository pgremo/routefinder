(ns routefinder.version)

(defn version
  []
  (-> version (. (getClass)) (. (getPackage)) (. (getImplementationVersion))))
