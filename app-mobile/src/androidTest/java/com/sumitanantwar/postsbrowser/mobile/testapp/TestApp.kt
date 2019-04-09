package com.sumitanantwar.postsbrowser.mobile.testapp

import android.app.Activity
import android.app.Application
import androidx.test.InstrumentationRegistry
import com.sumitanantwar.postsbrowser.mobile.testapp.di.DaggerTestAppComponent
import com.sumitanantwar.postsbrowser.mobile.testapp.di.TestAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class TestApp : Application(), HasActivityInjector {

    //======= Test Activity Injector =======
    @Inject
    lateinit var testActivityInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> {
        return testActivityInjector
    }

    // ======= Dagger AppComponent =======
    private val testAppComponent by lazy {

        DaggerTestAppComponent.builder()
            .application(this)
            .build()
    }


    // ======= Application Lifecycle =======
    override fun onCreate() {
        super.onCreate()

        // Inject the Application
        testAppComponent.inject(this)
    }

    //======= Static Accessor =======
    companion object {
        fun appComponent(): TestAppComponent {
            val testApp = InstrumentationRegistry.getTargetContext().applicationContext as TestApp
            return testApp.testAppComponent
        }
    }

}