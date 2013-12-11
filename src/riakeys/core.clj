(ns riakeys.core
  (:require [clojurewerkz.welle.core :as wc]
            [riakeys.cache :as cache]
            [riakeys.scanner :as scanner]))

(defn riakeys
  "Starts a riakeys service."
  []
  (let [riak (wc/connect-to-cluster-via-pb ["127.0.0.1"])
        cache (cache/cache)]
    {:riak    riak
     :cache   (cache/cache)
     :scanner (scanner/scanner riak cache)}))

(defn stop!
  "Stops a riakeys service."
  [riakeys]
  (scanner/stop! (:scanner riakeys)))
