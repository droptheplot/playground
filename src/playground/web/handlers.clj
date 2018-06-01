(ns playground.web.handlers
  (:require [ring.util.response :refer [response file-response status]]
            [playground.web.templates :as templates]
            [playground.history.core :as history])
  (:use [clojure.string :only (blank?)]))

(defn index [request]
  (response (templates/index)))

(defn history [{{{id :value} "ring-session"} :cookies }]
  (response (history/all id)))

(defn execute [{body :json-params {{id :value} "ring-session"} :cookies }]
  (let [item (get body "item")]
    (if (blank? item)
      (status {} 422)
      (do
        (history/add id item)
        (status {} 201)))))

(defn resource [{{path :path} :route-params}]
  (file-response path {:root "resources"}))
