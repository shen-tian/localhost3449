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

(defn angle-rad! [rpm]
  (* 2 (.-PI js/Math) (/ (angle! rpm) 360)))

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
    [:svg {:width 350
           :max-width "100%"
           :viewBox "0 0 200 200"}
     [:g {:transform "translate(100,100)"}
      [:circle {:r 100
                :fill :purple}]
      [:circle {:r 97 
                :fill :lightgreen}]
      [:circle {:r 89
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
    [:p "Try " [:code "lein figwheel"] " first?"]
    [:p [:a {:href "https://github.com/bhauman/lein-figwheel"}
         "Go cut some fruit"]]]
   [:div {:style {:width "100%"
                  :height 100
                  :position "fixed"
                  :bottom 0
                  ;;:background-color :purple
                  }}
    [:svg {:width "100%"
           :height 100
           :preserveAspectRatio "none"
           :viewBox "0 0 100 100"}
     (let [n 50]
       (into [:g]
             (for [x (map #(* (/ 100 n) %) (range n))]
               (let [w (inc (/ 100 n))
                     h (+ (* 50 (.sin js/Math 
                                      (+ (angle-rad! 11) 
                                         (/ x 10 (.-PI js/Math))))) 50)]
                 [:rect {:x x :y (- 100 h) 
                         :width w :height h 
                         :fill :lightgreen}]))))
     (let [n 50]
       (into [:g]
             (for [x (map #(* (/ 100 n) %) (range n))]
               (let [w (inc (/ 100 n))
                     h (+ (* 30 (.sin js/Math 
                                      (+ (angle-rad! 17) 
                                         (/ x 10 (.-PI js/Math))))) 50)]
                 [:rect {:x x :y (- 100 h) 
                         :width w :height h 
                         :fill :fuchsia}]))))
     (let [n 50]
       (into [:g]
             (for [x (map #(* (/ 100 n) %) (range n))]
               (let [w (inc (/ 100 n))
                     h (+ (* 30 (.sin js/Math 
                                      (+ (angle-rad! 21) 
                                         (/ x 10 (.-PI js/Math))))) 
                          30)]
                 [:rect {:x x :y (- 100 h) 
                         :width w :height h 
                         :fill :purple}]))))]]])



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
