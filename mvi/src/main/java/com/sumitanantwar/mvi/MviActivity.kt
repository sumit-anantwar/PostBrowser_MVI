package com.sumitanantwar.mvi

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.sumitanantwar.mvi.base.MviIntent
import com.sumitanantwar.mvi.base.MviView
import com.sumitanantwar.mvi.base.MviViewState

abstract class MviActivity<I: MviIntent, S: MviViewState> : BaseActivity(), MviView<I, S> {



    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)


    }

}