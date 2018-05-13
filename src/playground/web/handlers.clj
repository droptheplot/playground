(ns playground.web.handlers
  (:require [ring.util.response :refer [response file-response content-type]]
            [playground.web.templates :as templates]))

(defn index [request]
  (-> (response (templates/index))
      (content-type "text/html")))

(defn resource [{{path :path} :route-params}]
  (file-response path {:root "resources"}))
