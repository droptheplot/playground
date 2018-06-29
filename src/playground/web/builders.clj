(ns playground.web.builders
  (:require [cljs.build.api :as cljs]
            [garden.core :as garden]
            [hawk.core :as hawk]
            [clojure.core.async :as async])
  (:use [playground.web.css.core :only [main]]))

(defn watch-js []
  (async/go (cljs/watch "src" {:main 'playground.web.js.core
                             :output-to "resources/js/main.js"
                             :output-dir "resources/js"
                             :asset-path "js"})))

(defn watch-css []
  (hawk/watch! {:watcher :polling}
               [{:paths ["src/playground/web/css"]
                 :handler (fn [ctx e]
                            (garden/css {:output-to "resources/css/main.css"}
                                        (playground.web.css.core/main))
                            ctx)}]))
