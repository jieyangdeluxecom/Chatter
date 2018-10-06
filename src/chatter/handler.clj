(ns chatter.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [hiccup.page :as page]))


(defn generatemssage
  "this function display message"
  []
  (page/html5
      [:head
       [:title "chatter"]]
      [:body
       [:h1 "Our Chat App"]]))
(defroutes app-routes
  (GET "/" [](generatemssage))
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
