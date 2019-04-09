package com.sumitanantwar.postsbrowser.mobile.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import butterknife.BindView
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.ivianuu.contributer.conductor.HasControllerInjector
import com.sumitanantwar.mvi.BaseActivity
import com.sumitanantwar.postsbrowser.mobile.R
import com.sumitanantwar.postsbrowser.mobile.ui.postlist.PostsListController
import com.sumitanantwar.mvi.base.ActionBarProvider
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import javax.inject.Inject

// ======= Intent =======
fun Context.MainActivityIntent() : Intent {
    return Intent(this, MainActivity::class.java).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    }
}


class MainActivity : BaseActivity(),
    ActionBarProvider,
    HasControllerInjector {


    //======= Controller Injector =======
    @Inject
    lateinit var controllerInjector: DispatchingAndroidInjector<Controller>
    override fun controllerInjector(): AndroidInjector<Controller> {
        return controllerInjector
    }

    // Butterknife Bindings
    @BindView(R.id.controller_container)
    lateinit var container: ViewGroup

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

//    // Injection
//    @Inject
//    lateinit var postsListController: PostsListController

    // Conductor Router
    private lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        AndroidInjection.inject(this)

        setSupportActionBar(toolbar)

        router = Conductor.attachRouter(this, container, savedInstanceState)
        if (!router.hasRootController()) {
            router.setRoot(RouterTransaction.with(PostsListController()))
        }
    }

    override fun onBackPressed() {
        if (!router.handleBack()) {
            super.onBackPressed()
        }
    }

}
