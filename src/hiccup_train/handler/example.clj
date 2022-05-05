(ns hiccup-train.handler.example
  (:require   [integrant.core :as ig]
              [reitit.ring :as ring]
              [muuntaja.core :as m]
              [reitit.ring.middleware.muuntaja :as muuntaja]
              [ring.util.response :as res]
              [reitit.ring.middleware.parameters :as params]
              [hiccup.core :as h]
              [hiccup.form :as hf]
              [hiccup.page :as hp]
              [ring.util.anti-forgery]
              [incanter.core :as incanter]
              [incanter.charts :as charts]
              ))

(defn html [body]
  (-> body
      (res/response)
      (res/content-type  "text/html; charset=utf-8")))

(defn index [_]
  (html (h/html
         [:h1 "index"]
         [:p "test"])))

(defn result [{{:keys [title]} :params}] ;[req]
  (html (h/html
         [:h1 "result"]
         [:p "test"]
         [:p title])))

#_(defn form [_]
  (html
   (f/form-to [:post "/form"]
              (f/label "shout" "What do you want to SHOUT?")
              [:br]
              (f/text-area "shout")
              [:br]
              (f/submit-button "SHOUT!"))))

(defn todo-new-view [_]
  (html (h/html [:section.card
                 [:h2 "TODO 追加"]
                 (hf/form-to
                  [:post "/result"]
                  (ring.util.anti-forgery/anti-forgery-field)
                  [:p "anti"]
                  [:input {:name :title :placeholder "TODO を入力してください"}]
                  [:button.bg-blue "追加する"])])))


(defn make-graph[file_name]
  (let [my-plot (charts/function-plot incanter/sin -10 10)]
    (incanter/save my-plot "plot.png"))  )

(defn graph [_]
  (make-graph "")
  (html (h/html
         [:div "test"]
         [:img {:src "plot.png"}])))

(defmethod ig/init-key :hiccup-train.handler/example [_ options]
  (ring/ring-handler
   (ring/router
    [["/" {:get index}]
     ["/form" {:get todo-new-view}]
     ["/result" {:post result}]
     ["/graph" {:get graph}]]
    {:data {:muuntaja m/instance
            :middleware [muuntaja/format-middleware
                         params/parameters-middleware]}})))
