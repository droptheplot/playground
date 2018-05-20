(require '[cljs.build.api :as js])
(require '[garden.core :as css])

(use '[playground.web.css.core :only [main]])

(js/build "src" {:main 'playground.web.js.core
                 :output-to "resources/js/main.js"
                 :output-dir "resources/js"
                 :asset-path "js"})

(css/css {:output-to "resources/css/main.css"}
         (playground.web.css.core/main))
