(ns riakeys.scanner
  (:require [riakeys.cache :as cache]
            [clojurewerkz.welle.core :as wc]
            [clojurewerkz.welle.kv :as wk]))

(defn scan!
  "Initiates a scan of the keyspace."
  [riak cache]
  (wc/with-client riak
    (let [buckets (wk/index-query "test")]
      (prn buckets))))

(defn scanner
  "Starts a scanner service. Returns a service which can be stopped via stop!"
  [riak cache]
  (let [stopped (promise)]
    (future
      (while (not (deref stopped 1000 false))
        (try
          (scan! riak cache)
          (catch Exception e
            (prn "Caught" e))))
      (prn "Scanner exiting."))
    stopped))

(defn stop!
  "Stops a scanner."
  [scanner]
  (deliver scanner true))
