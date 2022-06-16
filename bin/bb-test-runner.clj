;; run via ./bin/bb-test-runner.sh
(ns bb-test-runner
  (:require [clojure.test :as t]
            [babashka.classpath :as cp]))

;;TODO
(def test-nsyms [#_'java-time-test
                 'java-time.graph-test])

(some->> (seq test-nsyms)
         (apply require))

(def test-results
  (apply t/run-tests test-nsyms))

(def failures-and-errors
  (let [{:keys [:fail :error]} test-results]
    (+ fail error)))

(System/exit failures-and-errors)
