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

(defn angle! [rpm]
  (mod (/ (.getTime @timer) rpm) 360))

(defn ring [count radius child]
  (into [:g] (for [theta (map #(* 360 (/ % count)) (range count))]
               [:g {:transform (str "rotate(" theta " 0 0)"
                                    "translate(" 0 " " radius ")")} 
                child])))

(def pip 
  [:g
   [:circle {:cy 3 :r 5 :fill :orange}]
   [:circle {:r 3 :fill :white}]])

(defn page []
  [:div.page
   [:div.fig
    #_[:img {:src "/img/fig.png"
           :style {:transform (str "rotate(" 
                                   (/ (.getTime @timer) 10)
                                   "deg)")}}]
    [:svg {:height 200
           :width 200}
     [:g {:transform "translate(100,100)"}
      [:circle {:cx 0 :cy 0 :r 100 
                :fill :lightgreen
                :stroke :purple
                :stroke-width 3}]
      [:circle {:cx 0 :cy 0 :r 75
                :fill :fuchsia}]
      (into [:g] (for [{:keys [rpm n radius]} 
                       [{:rpm 6  :n 8 :radius  10}
                        {:rpm 8  :n 10 :radius 20}
                        {:rpm 10 :n 12 :radius 30}
                        {:rpm 12 :n 16 :radius 40}
                        {:rpm 16 :n 20 :radius 50}
                        {:rpm 20 :n 24 :radius 60}
                        {:rpm 24 :n 28 :radius 70}
                        {:rpm 28 :n 32 :radius 80}]]
                   [:g {:transform (str "rotate(" (angle! rpm) " 0 0)")}
                    [ring n radius pip]]))]]]
   [:div.text
    [:p "Maybe " [:code "lein figwheel"] " first?"]]])



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
