package com.sumitanantwar.mvi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import butterknife.ButterKnife
import butterknife.Unbinder
import com.bluelinelabs.conductor.Controller
import com.sumitanantwar.mvi.base.ActionBarProvider

abstract class BaseController : Controller {

    //======= Initializers =======
    protected constructor() : super()
    protected constructor(args: Bundle) : super(args)

    //======= Anstract =======
    protected abstract val layoutId: Int
    protected abstract fun onViewBound(view: View)

    //======= Private =======
    private lateinit var unbinder: Unbinder


    //======= Controller Lifecycle =======
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val v = inflater.inflate(layoutId, container, false)

        // ButterKnife Binder
        unbinder = ButterKnife.bind(this, v)

        // Delegate view bound
        onViewBound(v)

        // Return inflated view
        return v
    }


    override fun onDestroyView(view: View) {
        super.onDestroyView(view)
        // ButterKnife Unbinder
        unbinder.unbind()
    }

    fun setTitle(title: String?) {
        getSupportActionBar()?.title = title
    }

    fun setDisplayHomeAsUpEnabled(enabled: Boolean) {
        setHasOptionsMenu(enabled)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(enabled)
    }

    private fun getSupportActionBar(): ActionBar? {

        return (activity as? ActionBarProvider)?.supportActionBar
    }

}