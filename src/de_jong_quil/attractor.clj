(ns de-jong-quil.attractor
  (:require [quil.core :as q]))

(def num-points (bit-shift-left 1 20))

(defn random-points []
  (repeatedly (fn [] [(- (rand 4.0) 2.0)
                      (- (rand 4.0) 2.0)])))

(defn de-jong [a b c d]
  (fn [[x y]]
    [(- (Math/sin (* a y)) (Math/cos (* b x)))
     (- (Math/sin (* c x)) (Math/cos (* d y)))]))

(defn setup []
  (q/frame-rate 1)
  (q/color-mode :hsb)
  ; (q/no-stroke)
  (let [f (de-jong 3.14 3.14 3.14 3.14)
        points (map (comp f f f f f) (take num-points (random-points)))]
    {:color 0
     :points points}))

(defn draw-state [state]
  ; Clear the sketch by filling it with light-grey color.
  (q/background 255)
  (q/stroke 0 255 255 60)

  ; (q/camera (/ (q/width) 2) (/ (q/height) 2) 40 (/ (q/width) 2) (/ (q/height) 2) 0 0 1 0)

  (let [w-scale (/ (q/width) 4)
        h-scale (/ (q/height) 4)]
    (q/with-translation [(/ (q/width) 2)
                         (/ (q/height) 2)]
      (q/begin-shape :points)
      (doseq [[idx [x y]] (map-indexed vector (:points state))]
        (q/vertex (* x w-scale 0.8) (* y h-scale 0.8)))
      (q/end-shape)))
  (q/exit))
