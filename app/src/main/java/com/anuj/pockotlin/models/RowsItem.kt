package com.anuj.pockotlin.models

data class RowsItem(val imageHref: String?, val description: String?, val title: String?) {

    override fun equals(other: Any?): Boolean {
        if (super.equals(other))
            return true
        else if (other is RowsItem) {
            val i = other as RowsItem?
            return this.title == i?.title && this.description == i?.description && this.imageHref == i?.imageHref
        } else
            return false
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}
