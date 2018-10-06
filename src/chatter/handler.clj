(ns chatter.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [hiccup.page :as page]
            [hiccup.form :as form]
            [ring.middleware.params :refer [wrap-params]]))


(def chat-messages (atom ()));this is a variable



(defn generateMssage ; this is a methid
  "view message"
  [d]
  (page/html5
      [:head
       [:title "chatter"]]
      [:body
       [:h1 "Our Chat App"]
        [:p
          (form/form-to
            [:post "/"]
            "Name: " (form/text-field "name")
            "Message: " (form/text-field "msg")
            (form/submit-button "Submit"))]
        [:P
          [:table
          (map (fn [m] [:tr [:td (:name m)] [:td (:message m)]]) d)]]]))

(defn updateMessage
  "update message"
  [d name new-message]
  (swap! d conj {:name name :message new-message}))

(defroutes app-routes
  (GET "/" [](generateMssage @chat-messages))
  (POST "/" {input :params}(generateMssage (updateMessage chat-messages
    (get input "name") (get input "msg"))))
  (route/not-found "Not Found"))

(def app (wrap-params app-routes))  ; the default one works for Get method, but does not work for the POST method for security reason
