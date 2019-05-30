package com.ninis.rxbindingrv.adapter.rx

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.jakewharton.rxbinding2.InitialValueObservable
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable

class EditTextChangeObservable(val editText: EditText): InitialValueObservable<CharSequence>() {
    override fun getInitialValue(): CharSequence {
        return editText.text
    }

    override fun subscribeListener(observer: Observer<in CharSequence>) {
        val listener = Listener(observer)
        editText.addTextChangedListener(listener)
        observer.onSubscribe(listener)
    }

    class Listener(
        private val observer: Observer<in CharSequence>
    ) : MainThreadDisposable(), TextWatcher {
        override fun onDispose() {
        }

        override fun afterTextChanged(edit: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            if( !isDisposed ) {
                observer.onNext(s)
            }
        }

    }
}