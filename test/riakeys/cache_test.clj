(ns riakeys.cache-test
  (:require [clojure.test :refer :all]
            [riakeys.cache :refer :all]))

(deftest cache-test
  (let [c (cache)]
    (update-key! c "foo" "bar")
    (update-key! c "foo" "baz")
    (update-key! c "foo" "bar")
    (update-key! c "cat" "meow")
    (is (= (bucket-keys c "foo") #{"bar" "baz"}))
    (is (= (bucket-keys c "cat") #{"meow"}))
    (remove-key! c "cat" "meow")
    (is (= 1 (count (:key-cache c))))
    (is (= (bucket-keys c "cat") #{}))))
