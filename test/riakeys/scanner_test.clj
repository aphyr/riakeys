(ns riakeys.scanner-test
  (:require [clojure.test :refer :all]
            [riakeys.scanner :refer :all]
            [riakeys.cache :as cache]
            [clojurewerkz.welle.core :as wc]
            [clojurewerkz.welle.kv :as wk]))

(defn fill!
  [riak]
  (wc/with-client riak
    (dotimes [n 1000]
      (wk/store "test" (str (rand-int 1000000)) "hi"))))

(deftest scan-test
  (let [riak  (wc/connect-to-cluster-via-pb ["127.0.0.1"])
        cache (cache/cache)]
    (fill! riak)
    (scan! riak cache)))
