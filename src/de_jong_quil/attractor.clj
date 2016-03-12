(ns de-jong-quil.attractor
  (:require [quil.core :as q]))

(def num-frames 10)
(def num-points (bit-shift-left 1 18))

(defn random-points []
  (repeatedly (fn [] [(- (rand 4.0) 2.0)
                      (- (rand 4.0) 2.0)])))

(defn de-jong [a b c d]
  (fn [[x y]]
    [(- (Math/sin (* a y)) (Math/cos (* b x)))
     (- (Math/sin (* c x)) (Math/cos (* d y)))]))

(defn setup []
  (q/frame-rate 60)
  (q/color-mode :hsb)
  (q/no-stroke)
  {:color 0
   :points (take num-points (random-points))})

(defn update-state [state]
  (let [f (de-jong 3.14 3.14 3.14 3.14)]
    {:color 0
     :points (map f (:points state))}))

(defn draw-state [state]
  (if (= (q/frame-count) num-frames)
    (q/exit))
  ; Clear the sketch by filling it with light-grey color.
  (q/background 240)

  (let [w-scale (/ (q/width) 4)
        h-scale (/ (q/height) 4)]
    (q/with-translation [(/ (q/width) 2)
                         (/ (q/height) 2)]
      (doseq [[idx [x y]] (map-indexed vector (:points state))]
        ; Set point color.
        (q/fill (* (/ idx num-points) 20) 255 255 25)
        (q/ellipse (* x w-scale) (* y h-scale) 1 1)))))
