package com.ninis.rxbindingrv.adapter.model

class RatingItemModel(
    type: Int, position: Int
) : BaseItemModel(type, position) {

    var rating: Float = 0f
}