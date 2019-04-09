package com.sumitanantwar.mvi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sumitanantwar.mvi.base.MviIntent
import com.sumitanantwar.mvi.base.MviView
import com.sumitanantwar.mvi.base.MviViewState
import io.reactivex.disposables.CompositeDisposable

abstract class MviController<I: MviIntent, S: MviViewState> : BaseController, MviView<I, S> {

    //======= Initializers =======
    protected constructor() : super()
    protected constructor(args: Bundle) : super(args)

    protected val disposables = CompositeDisposable()


    override fun onDestroyView(view: View) {
        super.onDestroyView(view)

        disposables.clear()
    }
}