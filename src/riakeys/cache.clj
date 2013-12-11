(ns riakeys.cache
  (:import (java.util.concurrent ConcurrentSkipListSet
                                 ConcurrentHashMap)))

(defprotocol Cache
  (update-key! [cache bucket key])
  (remove-key! [cache bucket key]))

;; A cache based on a ConcurrentSkipListSet.
(defprotocol ICSLSCache
  (^ConcurrentSkipListSet bucket-keys [cache bucket]))

(defrecord CSLSCache [^ConcurrentHashMap key-cache]
  ICSLSCache
  (bucket-keys [cache bucket]
               (or (get key-cache bucket)
                   (do
                     (.putIfAbsent key-cache bucket (ConcurrentSkipListSet.))
                     (get key-cache bucket))))

  Cache
  (update-key! [cache bucket key]
    (.add (bucket-keys cache bucket) key))

  (remove-key! [cache bucket key]
    (let [set (bucket-keys cache bucket)]
      (.remove set key)
      (when (.isEmpty set)
        (.remove key-cache bucket set)))))

(defn cache
  "Constructs a new cache service."
  []
  (CSLSCache. (ConcurrentHashMap.)))
