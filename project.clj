(require '[clojure.string :as str])
(def clojure-versions ["1.8" "1.9" "1.10" "1.11" "1.12"])
(def threeten-extra-version "1.4")
(def joda-time-version "2.10.1")
(defproject clojure.java-time "1.0.0-SNAPSHOT"
  :description "Clojure wrapper for Java 8 Time API"
  :url "http://github.com/dm3/clojure.java-time"
  :license {:name "MIT License"
            :url "http://opensource.org/licenses/MIT"}
  :scm {:name "git"
        :url "http://github.com/dm3/clojure.java-time"}
  :dependencies [[org.clojure/clojure "1.11.1" :scope "provided"]]
  :plugins []
  :profiles {:dev {:dependencies [[criterium "0.4.4"]
                                  [com.taoensso/timbre "5.2.1"]
                                  [com.taoensso/tufte "2.2.0"]
                                  [org.clojure/tools.namespace "1.3.0"]
                                  [joda-time/joda-time ~joda-time-version]
                                  [org.threeten/threeten-extra ~threeten-extra-version]]
                   :plugins [[lein-codox "0.10.8"]
                             [jonase/eastwood "1.2.3"]]
                   
                   :source-paths ["dev"]
                   :global-vars {*warn-on-reflection* true}
                   :eastwood {:exclude-namespaces [java-time
                                                   ;;FIXME
                                                   java-time-test]
                              :exclude-linters []}}
             ;; lein doc
             :codox {:injections [(require 'java-time)
                                  (require 'java-time.dev.gen)
                                  (java-time.dev.gen/spit-java-time-ns)]
                     :codox {:namespaces [java-time java-time.repl]
                             :doc-files ["README.md" "CHANGELOG.md"]
                             :metadata {:doc/format :markdown}
                             :output-path "docs"
                             :source-uri "https://github.com/dm3/clojure.java-time/blob/{git-commit}/{filepath}#L{line}"}}
             :async-profiler
             {:jvm-opts ["-Djdk.attach.allowAttachSelf" "-XX:+UnlockDiagnosticVMOptions" "-XX:+DebugNonSafepoints"]
              :dependencies [[com.clojure-goes-fast/clj-async-profiler "0.3.1"]]}
             :1.8 {:dependencies [[org.clojure/clojure "1.8.0"]]}
             :1.8-three-ten-joda {:dependencies [[org.clojure/clojure "1.8.0"]
                                                 [org.threeten/threeten-extra ~threeten-extra-version]
                                                 [joda-time/joda-time ~joda-time-version]]}
             :1.9 {:dependencies [[org.clojure/clojure "1.9.0"]]}
             :1.9-three-ten-joda {:dependencies [[org.clojure/clojure "1.9.0"]
                                                 [org.threeten/threeten-extra ~threeten-extra-version]
                                                 [joda-time/joda-time ~joda-time-version]]}
             :1.10 {:dependencies [[org.clojure/clojure "1.10.3"]]}
             :1.10-three-ten-joda {:dependencies [[org.clojure/clojure "1.10.3"]
                                                  [org.threeten/threeten-extra ~threeten-extra-version]
                                                  [joda-time/joda-time ~joda-time-version]]}
             :1.11 {:dependencies [[org.clojure/clojure "1.11.1"]]}
             :1.11-three-ten-joda {:dependencies [[org.clojure/clojure "1.11.1"]
                                                  [org.threeten/threeten-extra ~threeten-extra-version]
                                                  [joda-time/joda-time ~joda-time-version]]
                                   :repositories [["sonatype-oss-public" {:url "https://oss.sonatype.org/content/groups/public"}]]}
             :1.12 {:dependencies [[org.clojure/clojure "1.12.0-master-SNAPSHOT"]]
                    :repositories [["sonatype-oss-public" {:url "https://oss.sonatype.org/content/groups/public"}]]}
             :1.12-three-ten-joda {:dependencies [[org.clojure/clojure "1.12.0-master-SNAPSHOT"]
                                                  [org.threeten/threeten-extra ~threeten-extra-version]
                                                  [joda-time/joda-time ~joda-time-version]]
                                   :repositories [["sonatype-oss-public" {:url "https://oss.sonatype.org/content/groups/public"}]]}}
  :aliases {"all" ["with-profile" ~(str/join ":" (mapcat (juxt identity #(str % "-three-ten-joda")) clojure-versions))]
            "warm-deps" ["all" "deps"]
            "doc" ["with-profile" "-user,+codox" "codox"]
            "test-all" ["all" "test"]})
