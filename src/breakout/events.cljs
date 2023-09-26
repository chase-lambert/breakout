(ns breakout.events
  (:require
   [breakout.db :as db]
   [breakout.game :as g]
   [re-frame.core :as rf]))
   

(rf/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))

(rf/reg-event-db
  ::select-color
  (fn [db [_ idx]]
    (assoc db :color idx)))

(rf/reg-event-db
  ::create-block
  (fn [db [_ [x y]]]
    (update db :blocks 
            assoc [(g/px->col x)
                   (g/px->row y)]
            (:color db))))
            
(rf/reg-event-fx
  ::block-clicked
  (fn [cofx [_ col row]]
    (if (= (-> cofx :db :color) 5)
      {:dispatch [::remove-block [col row]]}
      {:dispatch [::update-block [col row]]})))

(rf/reg-event-db
  ::update-block
  (fn [db [_ coords]]
    (update db :blocks assoc coords (:color db))))

(rf/reg-event-db 
  ::remove-block
  (fn [db [_ coords]]
    (update db :blocks dissoc coords)))
