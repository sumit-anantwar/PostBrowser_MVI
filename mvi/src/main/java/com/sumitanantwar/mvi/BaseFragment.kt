package com.sumitanantwar.mvi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import butterknife.ButterKnife
import butterknife.Unbinder
import dagger.android.support.DaggerFragment
import timber.log.Timber

/**
 * Created by Sumit Anantwar on 4/26/18.
 */
abstract class BaseFragment : DaggerFragment() {

    //======= Anstract =======
    protected abstract val layoutId     :Int


    // ButterKnife Unbinder handle
    private lateinit var unbinder: Unbinder


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(layoutId, container, false)

        // ButterKnife Binder
        unbinder = ButterKnife.bind(this, v)

        // Return Inflated View
        return v;
    }


    override fun onDestroy() {
        super.onDestroy()
        // ButterKnife Unbinder
        unbinder.unbind()
    }



    // Helper Methods
    fun showProgress(show: Boolean) {
        Timber.i(if (show) "Progress Show" else "Progreee Hide")
    }

    fun showMessage(message: String) {
        showToast(message)
    }

    private fun showToast(@StringRes stringResourceId: Int) {
        showToast(this.getString(stringResourceId))
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}