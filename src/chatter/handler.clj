(ns chatter.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [hiccup.page :as page]
            [hiccup.form :as form]))


(def chat-messages [{:name "blue" :message "hello, world"}
  {:name "red" :message "red is my favorite color"}
  {:name "green" :message "green makes it go faster"}])


(defn generatemssage
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

(defroutes app-routes
  (GET "/" [](generatemssage chat-messages))
  (POST "/" [](generatemssage chat-messages))
  (route/not-found "Not Found"))

(def app app-routes)
