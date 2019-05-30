package com.ninis.rxbindingrv.adapter.rx

import android.widget.RatingBar
import com.jakewharton.rxbinding2.InitialValueObservable
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable

class RatingBarRatingChangeObservable(val ratingBar: RatingBar): InitialValueObservable<Float>() {
    override fun getInitialValue(): Float {
        return ratingBar.rating
    }

    override fun subscribeListener(observer: Observer<in Float>) {
        val listener = Listener(observer)
        ratingBar.onRatingBarChangeListener = listener
        observer.onSubscribe(listener)
    }

    class Listener(
        private val observer: Observer<in Float>
    ): MainThreadDisposable(), RatingBar.OnRatingBarChangeListener {

        override fun onDispose() {

        }

        override fun onRatingChanged(ratingBar: RatingBar?, rating: Float, fromUser: Boolean) {
            if( !isDisposed ) {
                observer.onNext(rating)
            }
        }

    }
}