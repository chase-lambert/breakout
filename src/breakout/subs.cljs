(ns breakout.subs
  (:require
   [re-frame.core :as rf]))

(rf/reg-sub
 ::color
 (fn [db]
   (:color db)))

(rf/reg-sub
  ::blocks
  (fn [db]
    (map (fn [[[col row] color]]
           [col row color])
         (:blocks db))))

