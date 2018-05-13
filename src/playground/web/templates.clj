(ns playground.web.templates
  (:use hiccup.core)
  (:use hiccup.page)
  (:use hiccup.element))

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

(defn index []
  (html
    (head "Playground")
    [:body
     [:div.container-fluid]]))
