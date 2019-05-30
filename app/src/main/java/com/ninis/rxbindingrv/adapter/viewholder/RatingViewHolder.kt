package com.ninis.rxbindingrv.adapter.viewholder

import android.view.View
import com.ninis.rxbindingrv.adapter.model.BaseItemModel
import com.ninis.rxbindingrv.adapter.model.RatingItemModel
import com.ninis.rxbindingrv.adapter.rx.RatingBarRatingChangeObservable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.vh_rating_item.view.*

class RatingViewHolder(
    itemView: View,
    callback: (id: Int, rating: Float) -> Unit,
    disposable: CompositeDisposable
) : BaseViewHolder(itemView) {

    init {
        disposable.add(RatingBarRatingChangeObservable(itemView.rb_star_five).subscribe {
            callback.invoke(0, it)
        })
    }

    override fun onBindView(model: BaseItemModel) {
        val ratingModel = model as RatingItemModel
        itemView.tv_rating_value.text = String.format("%.1f", ratingModel.rating)
    }
}