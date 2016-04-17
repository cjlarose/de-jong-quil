(ns de-jong-quil.attractor
  (:require [quil.core :as q]))

(def num-points 250)
(def GRID_SIZE 50)

(defn random-points []
  (repeatedly (fn [] [(- (rand 4.0) 2.0)
                      (- (rand 4.0) 2.0)])))

(defn de-jong [a b c d]
  (fn [[x y]]
    [(- (Math/sin (* a y)) (Math/cos (* b x)))
     (- (Math/sin (* c x)) (Math/cos (* d y)))]))

(defn probability-grid [points]
  (->> points
       (map (fn [[x y]] [(Math/round (* (+ x 2.0) (/ GRID_SIZE 4.0)))
                         (Math/round (* (+ y 2.0) (/ GRID_SIZE 4.0)))]))
       (frequencies)
       (reduce (fn [memo [k v]] (assoc memo k (/ (double v) (count points)))) {})))

(defn setup []
  (q/frame-rate 1)
  (q/color-mode :hsb)
  (q/no-stroke)
  (let [f (de-jong 3.14 3.14 3.14 3.14)
        points (map (comp f f f f f) (take num-points (random-points)))]
    {:color 0
     :points points}))

(defn draw-state [state]
  (q/background 255)
  (q/fill 0 255 255 60)

  (let [dx (/ (q/width) GRID_SIZE)
        dy (/ (q/height) GRID_SIZE)
        probs (probability-grid (:points state))]
    (doseq [col (range GRID_SIZE)
            row (range GRID_SIZE)]
      (let [prob (get probs [col row] 0.0)
            size (* (Math/pow prob 0.5) 100)]
        (q/ellipse (+ (* col dx) (/ dx 2)) (+ (* row dy) (/ dy 2)) size size)))))
