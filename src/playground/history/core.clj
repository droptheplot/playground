(ns playground.history.core
  (:require [ring.util.response :refer [response file-response content-type]]
            [taoensso.carmine :as car]))

(def cache-store {:pool {} :spec {:uri (System/getenv "REDIS_URL")}})
(def cache-namespace "history#")

(defn add [id value]
  (car/wcar cache-store
            (car/lpush (str cache-namespace id) value)))

(defn all
  ([id size] (car/wcar cache-store
                  (car/lrange (str cache-namespace id) 0 size)))
  ([id] (all id -1)))
