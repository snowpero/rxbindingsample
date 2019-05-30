package com.ninis.rxbindingrv.adapter

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ninis.rxbindingrv.R
import com.ninis.rxbindingrv.adapter.model.BaseItemModel
import com.ninis.rxbindingrv.adapter.model.EditTextItemModel
import com.ninis.rxbindingrv.adapter.model.RatingItemModel
import com.ninis.rxbindingrv.adapter.viewholder.BaseViewHolder
import com.ninis.rxbindingrv.adapter.viewholder.EditTextViewHolder
import com.ninis.rxbindingrv.adapter.viewholder.RatingViewHolder
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

class MainAdapter(val disposable: CompositeDisposable) : RecyclerView.Adapter<BaseViewHolder>() {

    private val TYPE_EDIT_TEXT = 0
    private val TYPE_RATING = 1

    private val items = ArrayList<BaseItemModel>()

    init {
        setTestData()
    }

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): BaseViewHolder {
        if (type == TYPE_RATING) {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.vh_rating_item, parent, false)
            return RatingViewHolder(itemView, ratingBarChangesCallBack, disposable)
        } else {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.vh_edittext_item, parent, false)
            return EditTextViewHolder(itemView, editTextChangesCallBack, disposable)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position < items.size)
            return items[position].type
        else
            return TYPE_EDIT_TEXT
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(viewHolder: BaseViewHolder, position: Int) {
        viewHolder.onBindView(items[position])
    }

    private val ratingBarChangesCallBack = { id: Int, rating: Float ->
//        val asd = Log.d("NINIS", "ratingBarChangesCallBack] " + rating)

        val dis =
            Observable.fromIterable(items.withIndex())
                .filter { it.value is RatingItemModel }
                .filter { rating > 0.5f }
                .doAfterTerminate {
                    Log.d("NINIS", "doAfterTerminate : " + itemCount)
                }
                .subscribe {
                    Log.d("NINIS", "ratingBarChangesCallBack sub] " + rating)
                    (items[it.index] as RatingItemModel).rating = rating
                }
    }

    private val editTextChangesCallBack = { input: CharSequence ->
//        val asd = Log.d("NINIS", "editTextChangesCallBack] " + input)

        val dis =
            Observable.fromIterable(items.withIndex())
                .filter { it.value is EditTextItemModel }
                .filter { input.isNotEmpty() }
                .subscribe {
                    Log.d("NINIS", "editTextChangesCallBack sub] " + input)
                    (items[it.index] as EditTextItemModel).input = input
                }
    }

    fun setTestData() {
        if (items.isNotEmpty())
            items.clear()

        for (index in 0 until 10) {
            if (index % 2 == 0) {
                items.add(EditTextItemModel(TYPE_EDIT_TEXT, index))
            } else {
                items.add(RatingItemModel(TYPE_RATING, index))
            }
        }
    }
}