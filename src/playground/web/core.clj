(ns playground.web.core
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.logger :as logger]
            [ataraxy.core :as ataraxy]
            [playground.web.handlers :as handlers])
  (:use ring.middleware.resource
        ring.middleware.content-type
        ring.middleware.not-modified
        ring.middleware.defaults))

(def handler
  (ataraxy/handler
    {:routes '{"/" [:index]
               [:post "/execute"] [:execute]
               [^{:re #"/(js|css)/(.+)"} path] [:resource path]}
     :handlers {:index handlers/index
                :execute handlers/execute
                :resource handlers/resource}}))

(defn -main [& args]
  (jetty/run-jetty
    (-> handler
        (wrap-resource "resources")
        (wrap-content-type)
        (wrap-defaults site-defaults)
        (wrap-reload)
        (logger/wrap-with-logger))
    {:port 8080}))
