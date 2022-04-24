(ns hiccup-train.handler.example
  (:require [compojure.core :refer :all]
            [clojure.java.io :as io]
            [integrant.core :as ig]))

(defmethod ig/init-key :hiccup-train.handler/example [_ options]
  (context "/example" []
    (GET "/" []
      (io/resource "hiccup_train/handler/example/example.html"))))
