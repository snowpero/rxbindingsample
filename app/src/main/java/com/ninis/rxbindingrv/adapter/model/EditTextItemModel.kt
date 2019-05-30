package com.ninis.rxbindingrv.adapter.model

class EditTextItemModel(
    type: Int, position: Int
) : BaseItemModel(type, position) {
    var input: CharSequence = ""
}