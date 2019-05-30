package com.ninis.rxbindingrv.adapter.rx

import android.widget.CompoundButton
import com.jakewharton.rxbinding2.InitialValueObservable
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable

/**
 * 체크박스, 토글버튼도 같이 사용
 */
class SwitchChangeObservable(val view: CompoundButton): InitialValueObservable<Boolean>() {
    override fun getInitialValue(): Boolean {
        return view.isChecked
    }

    override fun subscribeListener(observer: Observer<in Boolean>) {
        val listener = Listener(observer)
        view.setOnCheckedChangeListener(listener)
        observer.onSubscribe(listener)
    }

    class Listener(
        private val observer: Observer<in Boolean>
    ) : MainThreadDisposable(), CompoundButton.OnCheckedChangeListener {
        override fun onDispose() {
        }

        override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
            if( !isDisposed ) {
                observer.onNext(isChecked)
            }
        }
    }
}