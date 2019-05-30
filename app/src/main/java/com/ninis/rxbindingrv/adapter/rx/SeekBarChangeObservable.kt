package com.ninis.rxbindingrv.adapter.rx

import android.widget.SeekBar
import com.jakewharton.rxbinding2.InitialValueObservable
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable

class SeekBarChangeObservable(private val view: SeekBar): InitialValueObservable<Int>() {
    override fun getInitialValue(): Int {
        return view.progress
    }

    override fun subscribeListener(observer: Observer<in Int>) {
        val listener = Listener(observer)
        view.setOnSeekBarChangeListener(listener)
        observer.onSubscribe(listener)
    }

    class Listener(
        private val observer: Observer<in Int>
    ): MainThreadDisposable(), SeekBar.OnSeekBarChangeListener {
        override fun onDispose() {
        }

        override fun onProgressChanged(seekBar: SeekBar?, progrees: Int, fromUser: Boolean) {
            if( !isDisposed ) {
                observer.onNext(progrees)
            }
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {

        }

    }
}