(ns localhost3449.core
  (:require
   [reagent.core :as reagent]
   ))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Vars

(defonce timer (reagent/atom (js/Date.)))

(defonce time-updater (js/setInterval
                       #(reset! timer (js/Date.)) 16))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Page

(defn page []
  [:div
   [:div (.toT)]
   [:img {:src "/img/fig.png"
          :width "50%"
          :style {:transform (str "rotate(" 
                                  (/ (.getTime @timer) 10)
                                  "deg)")}}]
   [:span "Maybe " [:code "lein figwheel"] " first?"]])



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Initialize App

(defn dev-setup []
  (when ^boolean js/goog.DEBUG
    (enable-console-print!)
    (println "dev mode")))

(defn reload []
  (reagent/render [page]
                  (.getElementById js/document "app")))

(defn ^:export main []
  (dev-setup)
  (reload))
