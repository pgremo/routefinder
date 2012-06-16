(ns routefinder.views.solarsystems
  (:use routefinder.models.solarsystems
        noir.core
        hiccup.page
        hiccup.form)
  (:require [routefinder.views.common :as common]
            [noir.content.getting-started]
            [noir.response :as response]))

(defpage "/solarsystems/search.json" {:keys [term]}
  (response/json (map :SOLARSYSTEMNAME (solarsystems-by-name term))))

(defpage "/solarsystems/search" []
  (common/layout
    [(include-css "/css/themes/base/jquery.ui.all.css")
     (include-css "/css/solarsystems.css")
     (include-js "/js/string.js")
     (include-js "/js/jquery-1.7.2.min.js")
     (include-js "/js/ui/jquery.ui.core.js")
     (include-js "/js/ui/jquery.ui.widget.js")
     (include-js "/js/ui/jquery.ui.position.js")
     (include-js "/js/ui/jquery.ui.autocomplete.js")
     (include-js "/js/ui/jquery.ui.button.js")
     (include-js "/js/solarsystem.js")]
    (form-to [:post "/solarsystems/search"]
      [:div.ui-widget (label "solarsystem" "Solar System Name")
       (text-field "solarsystem")]
      [:div.ui-widget [:ol#log ]]) ))
