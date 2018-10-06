(ns chatter.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.adapter.jetty :as jetty]
            [hiccup.page :as page]
            [hiccup.form :as form]
            [ring.util.anti-forgery :as anti-forgery]
            [environ.core :refer [env]]))


(def chat-messages (atom ()));this is a variable (def chat-messages (atom '()))



(defn generateMssage ; this is a methid
  "view message"
  [d]
  (page/html5
      [:head
       [:title "chatter"]
       (page/include-css "//maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css")
       (page/include-js  "//ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js")
       (page/include-js  "//maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js")
       (page/include-css "/chatter.css")]
      [:body
       [:h1 "Our Chat App"]
        [:p
          (form/form-to
            [:post "/"]
            "Name: " (form/text-field "name")
            "Message: " (form/text-field "msg")
            (form/submit-button "Submit"))]
        [:P
          [:table#messages.table.table-bordered.table-hover.
          (map (fn [m] [:tr [:td (:name m)] [:td (:message m)]]) d)]]]))

(defn updateMessage
  "update message"
  [d name new-message]
  (swap! d conj {:name name :message new-message}))

(defn init []
  (println "chatter is starting"))


(defn destroy []
  (println "chatter is shutting down"))

(defroutes app-routes
  (GET "/" [](generateMssage @chat-messages))
  (POST "/" {input :params}(generateMssage (updateMessage chat-messages
    (get input "name") (get input "msg"))))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app (wrap-params app-routes))  ; the default one works for Get method, but does not work for the POST method for security reason

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty #'app {:port port :join? false})))
