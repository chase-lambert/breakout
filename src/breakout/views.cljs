(ns breakout.views
  (:require
   [re-frame.core :as rf]
   [breakout.styles :as styles]
   [breakout.events :as events]
   [breakout.subs :as subs]
   [breakout.views.helpers :refer [background-box translate click-pos]]
   [breakout.views.swatches :refer [color-swatch selection-marker]]
   [breakout.game :as g]))
   

(defn color-picker []
  (let [color-idx (rf/subscribe [::subs/color])]
    (fn []
      [:g
       [background-box {:x 0 :y 0 :width 60 :height 310}]
       (for [idx (range 6)]
         [color-swatch {:index idx
                        :on-click #(rf/dispatch [::events/select-color idx])
                        :key (str "color-" idx)}])
       [selection-marker {:index @color-idx}]])))

(defn block [{:keys [row column color]}]
  [:rect {:x (g/col->px column)
          :y (g/row->px row)
          :width g/block-width
          :height g/block-height
          :class (g/color-class color)
          :on-click #(rf/dispatch [::events/block-clicked column row])}])

(defn blocks []
  (let [blocks (rf/subscribe [::subs/blocks])]
    (fn []
      [:g
       (for [[column row color] @blocks]
         [block {:row row
                 :column column
                 :color color
                 :key (str row "--" column)}])])))

(defn main-panel []
  [:div.game
   [:h1 "BREAKOUT LEVEL EDITOR"]
   [:svg {:style {:width (+ g/width 70) :height g/height}}
    [background-box {:x 0 
                     :y 0 
                     :width g/width 
                     :height g/height
                     :on-click #(rf/dispatch [::events/create-block (click-pos %)])}]
    [blocks]
    [translate {:x 1009 :y 0}
     [color-picker]]]])
