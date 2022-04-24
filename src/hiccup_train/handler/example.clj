(ns hiccup-train.handler.example
  (:require   [integrant.core :as ig]
              [reitit.ring :as ring]
              [muuntaja.core :as m]
              [reitit.ring.middleware.muuntaja :as muuntaja]
              [ring.util.response :as res]
              [reitit.ring.middleware.parameters :as params]))

(defn ok [_]
  (-> "ok"
      (res/response)
      (res/content-type  "text/html; charset=utf-8")))

(defmethod ig/init-key :hiccup-train.handler/example [_ options]
  (ring/ring-handler
   (ring/router
    [["/" ok]]
    {:data {:muuntaja m/instance
            :middleware [muuntaja/format-middleware
                         params/parameters-middleware]}})))
