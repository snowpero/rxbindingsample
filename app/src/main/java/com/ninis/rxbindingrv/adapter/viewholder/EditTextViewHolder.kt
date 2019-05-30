package com.ninis.rxbindingrv.adapter.viewholder

import android.util.Log
import android.view.View
import com.ninis.rxbindingrv.adapter.model.BaseItemModel
import com.ninis.rxbindingrv.adapter.rx.EditTextChangeObservable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.vh_edittext_item.view.*
import java.util.concurrent.TimeUnit

class EditTextViewHolder(
    itemView: View,
    callback: (input: CharSequence) -> Unit,
    disposable: CompositeDisposable
) : BaseViewHolder(itemView) {

    init {
        disposable.add(EditTextChangeObservable(itemView.et_input)
            .debounce(5000, TimeUnit.MICROSECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    callback.invoke(it.toString())
                },
                {
                    Log.d("NINIS", it.message)
                }
            )
        )
    }

    override fun onBindView(model: BaseItemModel) {

    }
}