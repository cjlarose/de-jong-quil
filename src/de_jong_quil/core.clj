(ns de-jong-quil.core
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [de-jong-quil.attractor :refer [setup update-state draw-state]]))

(q/defsketch de-jong-quil
  :title "You spin my circle right round"
  :size [500 500]
  ; setup function called only once, during sketch initialization.
  :setup setup
  ; update-state is called on each iteration before draw-state.
  :update update-state
  :draw draw-state
  :features [:keep-on-top]
  ; This sketch uses functional-mode middleware.
  ; Check quil wiki for more info about middlewares and particularly
  ; fun-mode.
  :middleware [m/fun-mode])
