(ns example.t-api
  "Unittests for example app"
  (:require [midje.sweet :refer :all]
            [example.api :refer [format-github-user]]))


(facts "GitHub info formatting"
  (format-github-user {:name "Foo"
                       :company "Foo, Inc."
                       :blog "example.com"
                       :bio "Lorem Ipsum"
                       :email "foo@bar.ee"}) =>
  {:name "Foo" :company "Foo, Inc." :blog "example.com"})
