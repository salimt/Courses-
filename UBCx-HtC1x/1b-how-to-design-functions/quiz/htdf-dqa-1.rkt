;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-beginner-reader.ss" "lang")((modname img1-wider) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
(require 2htdp/image)

;; Image -> Boolean
;; consumes two images and decides whether the first image is larger or not in "true/false" form.
(check-expect (larger? (rectangle 60 40 "solid" "black") (rectangle 50 30 "solid" "black")) true)
(check-expect (larger? (rectangle 20 50 "solid" "black") (rectangle 40 60 "solid" "black")) false)
(check-expect (larger? (rectangle 40 40 "solid" "black") (rectangle 40 40 "solid" "black")) false)

;(define (larger? img1 img2) false) ;stub
;(define (larger? img1 img2)        ;template
;  (... img1 img2))

(define (larger? img1 img2)
  (and (> (image-width img1) (image-width img2))
       (> (image-height img1)(image-height img2))))