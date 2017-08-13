(ns localhost3449.css
  (:require [garden.def :refer [defstyles]]
            [garden.color :as color]))

(defstyles screen
  [[:body  {:padding 0
            :margin 0
            :background-color "#AB47BC"
            :font-family "-apple-system, BlinkMacSystemFont, \"Segoe UI\", Roboto, Helvetica, Arial, sans-serif, \"Apple Color Emoji\", \"Segoe UI Emoji\", \"Segoe UI Symbol\""}]
   [:.page {:display "flex"
            :flex-direction "column"
            :align-items "center"}]
   [:.fig {:margin "60px"}
    [:img {:width "300px"
           :max-width "100%"}]]
   [:.text {:font-size "24px"
            :color "white"}]
   [:code {:color "#E1BEE7"}]])
