(ns riakeys.core-test
  (:require [clojure.test :refer :all]
            [riakeys.core :refer :all]))

(deftest core-test
  (let [r (riakeys)]
    (try
      (prn r)
      (finally
        (Thread/sleep 5000)
        (stop! r)))))
