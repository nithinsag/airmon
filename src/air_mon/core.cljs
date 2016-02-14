(ns air-mon.core
  (:require [om.core :as om :include-macros true]
            [om-bootstrap.panel :as p]
            [om-bootstrap.button :as b]
            [om-bootstrap.nav :as n]
            [om.dom :as dom :include-macros true]))

(enable-console-print!)

(println "Edits to this text should show up in your developer console.")

;; define your app data so that it doesn't get over-written on reload

(defonce app-state (atom {:text "Hello world!"}))


(defn header-element
  [data owner]
  (reify
    om/IRender
    (render [_]
      (dom/nav #js {:className "navbar navbar-default
      navbar-static-top" }
                (dom/div #js {:className "container"}
                         (dom/div #js {:className "navbar-header"}
                                  (dom/a #js
                                  {:className "navbar-brand" :href "#"} "AirMonitor")

                                  ) ) ) ) ))


(defn map-element
  [data owner]
  (reify
    om/IDidMount
    (did-mount [_]
      (println "Map Mounted!")
      (let [map-canvas (om/get-node owner)
            map-options (clj->js {"center" (google.maps.LatLng. -34.397, 150.644)
                                  "zoom" 8})]
        (js/google.maps.Map. map-canvas map-options))

      )

    om/IRender
    (render [_]
      (dom/div #js {:id "map" :className "map"} ) ) ) )


(defn  root-element [data owner]
  (reify om/IRender
    (render [_]
      (dom/div {:id "root"}

               (om/build header-element nil)
               (om/build map-element nil)
               ))))
(om/root
 root-element
 app-state
 {:target (. js/document (getElementById "app"))})


(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
  )
