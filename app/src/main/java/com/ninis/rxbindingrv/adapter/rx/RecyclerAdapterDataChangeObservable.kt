package com.ninis.rxbindingrv.adapter.rx

import android.support.v7.widget.RecyclerView
import com.jakewharton.rxbinding2.InitialValueObservable
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable

//fun <T : RecyclerView.Adapter<out RecyclerView.ViewHolder>> T.dataChanges(): InitialValueObservable<T> =
//        RecyclerAdapterDataChangeObservable(this)

class RecyclerAdapterDataChangeObservable<T : RecyclerView.Adapter<out RecyclerView.ViewHolder>>(
    private val adapter: T
) : InitialValueObservable<T>() {
    override fun getInitialValue(): T {
        return adapter
    }

    override fun subscribeListener(observer: Observer<in T>) {
        val listener = Listener(adapter, observer)
        observer.onSubscribe(listener)
        adapter.registerAdapterDataObserver(listener.dataObserver)
    }

    class Listener<T : RecyclerView.Adapter<out RecyclerView.ViewHolder>>(
        private val recyclerAdapter: T,
        observer: Observer<in T>
    ) : MainThreadDisposable() {

        val dataObserver = object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                if (!isDisposed) {
                    observer.onNext(recyclerAdapter)
                }
            }
        }

        override fun onDispose() {
            recyclerAdapter.unregisterAdapterDataObserver(dataObserver)
        }
    }
}