(ns playground.web.templates
  (:use hiccup.core
        hiccup.page
        hiccup.element
        hiccup.form
        ring.util.anti-forgery))

(defn- head [title]
  (html
    [:head
     [:title title]
     [:meta {:charset "utf-8"}]
     (include-js "js/app.js")
     (update-in (first (include-css "https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"))
                [1] assoc :integrity "sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB"
                          :crossorigin "anonymous")
     (include-css "css/app.css")]))

(defn input-form []
  (html
    [:form {:action "/execute" :method "POST"}
     (anti-forgery-field)
     [:div.form-group (text-area {:class "form-control" :rows 10} "input")]
     [:div.form-group (submit-button {:class "btn btn-primary"} "Submit")]]))

(defn index []
  (html
    (head "Playground")
    [:body
     [:div.container-fluid.p-5
      [:h1.mb-3 "Playground"]
      (input-form)]]))
