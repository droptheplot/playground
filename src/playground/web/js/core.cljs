(ns playground.web.js.core
  (:require [reagent.core :as r]
            [ajax.core :as ajx]))

(defonce state
  (r/atom {:history []}))

(defn load-history []
  (ajx/GET "/api/history"
           {:handler (fn [history] (swap! state assoc :history history))
            :response-format :json
            :keywords? true}))

(defn history-list []
  [:ul.list-group.list-group-flush
   (for [item (:history @state)]
     [:li.list-group-item item])])

(defn execute [item]
  (ajx/POST "/api/execute"
            {:params {:item item}
             :format (ajx/json-request-format)
             :finally load-history}))

(defn execute-form []
  (let [val (r/atom "")]
    (fn []
      [:div
       [:input {:type "text"
                :value @val
                :on-change #(reset! val (-> % .-target .-value))}]
       [:button {:on-click #(when-let [v @val]
                              (execute v)
                              (reset! val ""))}
        "Execute"]])))

(defn ^:export run []
  (load-history)
  (r/render [history-list] (js/document.getElementById "history-list"))
  (r/render [execute-form] (js/document.getElementById "execute-form")))
