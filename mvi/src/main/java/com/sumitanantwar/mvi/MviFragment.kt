package com.sumitanantwar.mvi

import com.sumitanantwar.mvi.base.MviIntent
import com.sumitanantwar.mvi.base.MviView
import com.sumitanantwar.mvi.base.MviViewState
import io.reactivex.disposables.CompositeDisposable

abstract class MviFragment<I: MviIntent, S: MviViewState> : BaseFragment(), MviView<I, S> {

    protected val disposables = CompositeDisposable()

    //======= Abstract =======
    protected abstract fun bindIntents()


    override fun onStart() {
        super.onStart()

        bindIntents()
    }

    override fun onStop() {
        super.onStop()

        disposables.clear()
    }

}