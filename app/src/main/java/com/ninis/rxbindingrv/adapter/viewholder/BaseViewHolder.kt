package com.ninis.rxbindingrv.adapter.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.ninis.rxbindingrv.adapter.model.BaseItemModel

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun onBindView(model: BaseItemModel)
}