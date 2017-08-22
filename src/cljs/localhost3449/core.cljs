(ns localhost3449.core
  (:require
   [reagent.core :as reagent]))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Vars

(defonce timer (reagent/atom (js/Date.)))

(defonce time-updater (js/setInterval
                       #(reset! timer (js/Date.)) 16))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Page

(defn angle! [rpm]
  (mod (* rpm (.getTime @timer) (/ 360 60000)) 360))

(defn ring [count radius child]
  (into [:g] (for [theta (map #(* 360 (/ % count)) (range count))]
               [:g {:transform (str "rotate(" theta " 0 0)"
                                    "translate(" 0 " " radius ")")} 
                child])))

(defn rotate! [rpm]
  {:transform (str "rotate(" (angle! (/ rpm 3)) " 0 0)")})

(def pip 
  [:g
   [:circle {:cy 3 :r 5 :fill :orange}]
   [:circle {:r 3 :fill :white}]])


(defn page []
  [:div.page
   [:div.fig
    [:svg {:height 300
           :width 300
           :viewBox "0 0 200 200"}
     [:g {:transform "translate(100,100)"}
      [:circle {:r 100
                :fill :purple}]
      [:circle {:r 97 
                :fill :lightgreen}]
      [:circle {:r 90
                :fill :fuchsia}]
      (into [:g] (for [{:keys [rpm n radius]} 
                       [{:rpm 78 :n 8  :radius 13}
                        {:rpm 65 :n 12 :radius 26}
                        {:rpm 52 :n 18 :radius 39}
                        {:rpm 39 :n 27 :radius 52}
                        {:rpm 26 :n 40 :radius 65}
                        {:rpm 13 :n 61 :radius 78}]]
                   [:g (rotate! rpm)
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
