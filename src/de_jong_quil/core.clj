(ns de-jong-quil.core
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [de-jong-quil.attractor :refer [setup draw-state]]))

(defn -main []
  (q/defsketch de-jong-quil
    :title "You spin my circle right round"
    :size [2400 2400]
    ; setup function called only once, during sketch initialization.
    :setup setup
    ; update-state is called on each iteration before draw-state.
    :draw draw-state

    :renderer :pdf
    :output-file "export.pdf"

    ; This sketch uses functional-mode middleware.
    ; Check quil wiki for more info about middlewares and particularly
    ; fun-mode.
    :middleware [m/fun-mode]))
