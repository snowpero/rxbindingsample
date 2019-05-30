package com.ninis.rxbindingrv.adapter.rx

import android.widget.RadioGroup
import com.jakewharton.rxbinding2.InitialValueObservable
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable

class RadioButtonChangeObservable(private val view: RadioGroup): InitialValueObservable<Int>() {
    override fun getInitialValue(): Int {
        return view.checkedRadioButtonId
    }

    override fun subscribeListener(observer: Observer<in Int>) {
        val listener = Listener(observer)
        view.setOnCheckedChangeListener(listener)
        observer.onSubscribe(listener)
    }

    class Listener(
        private val observer: Observer<in Int>
    ): MainThreadDisposable(), RadioGroup.OnCheckedChangeListener {
        override fun onDispose() {

        }

        override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
            if( !isDisposed ) {
                observer.onNext(checkedId)
            }
        }

    }
}