(ns playground.web.core
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.json :refer [wrap-json-response wrap-json-body wrap-json-params]]
            [ring.middleware.content-type :refer [wrap-content-type]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [ring.logger :refer [wrap-with-logger]]
            [ataraxy.core :as ataraxy]
            [playground.web.handlers :as handlers]
            [playground.web.builders :as builders]))

(defn api [handler]
  (-> handler
      (wrap-json-body)
      (wrap-json-params)
      (wrap-json-response)
      (wrap-params)
      (wrap-keyword-params)
      (wrap-reload)
      (wrap-with-logger)))

(defn site [handler]
  (-> handler
      (wrap-params)
      (wrap-keyword-params)
      (wrap-reload)
      (wrap-with-logger)))

(defn resource [handler]
  (-> handler
      (wrap-resource "resources")
      (wrap-content-type)))

(def handler
  (ataraxy/handler
    {:routes '{"/" ^:site [:index]
               [^{:re #"/(js|css)/(.+)"} path] ^:resource [:resource path]
               "/api" ^:api {"/history" [:history]
                             [:post "/execute"] [:execute]}}
     :handlers {:index handlers/index
                :history handlers/history
                :execute handlers/execute
                :resource handlers/resource}
     :middleware {:api api
                  :site site
                  :resource resource}}))

(defn -main [& args]
  (builders/watch-css)
  (builders/watch-js)
  (jetty/run-jetty handler {:port 8080}))
