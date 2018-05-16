(ns playground.web.handlers
  (:require [ring.util.response :refer [response file-response content-type]]
            [playground.web.templates :as templates]
            [playground.history.core :as history])
  (:use [clojure.string :only (blank?)]))

(defn index [{{{id :value} "ring-session"} :cookies }]
  (-> (response (templates/index (history/all id)))
      (content-type "text/html")))

(defn execute [{{input :input} :params {{id :value} "ring-session"} :cookies }]
  (if (blank? input)
    (-> (response "Fail")
        (content-type "text/html")))
    (do
      (history/add id input)
      (-> (response "Success")
          (content-type "text/html"))))

(defn resource [{{path :path} :route-params}]
  (file-response path {:root "resources"}))
