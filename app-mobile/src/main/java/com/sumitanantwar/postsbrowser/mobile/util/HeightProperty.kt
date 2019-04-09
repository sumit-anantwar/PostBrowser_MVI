package com.sumitanantwar.postsbrowser.mobile.util

import android.util.Property
import android.view.View


class HeightProperty : Property<View, Float>(Float::class.java, "height") {

    override operator fun get(view: View): Float? {
        return view.getHeight().toFloat()
    }

    override operator fun set(view: View, value: Float) {
        view.getLayoutParams().height = value.toInt()
        view.setLayoutParams(view.getLayoutParams())
    }
}