package com.sumitanantwar.postsbrowser.mobile.ui.splash

import android.os.Bundle
import android.os.Handler
import butterknife.BindView
import com.airbnb.lottie.LottieAnimationView
import com.sumitanantwar.mvi.BaseActivity
import com.sumitanantwar.postsbrowser.mobile.R
import com.sumitanantwar.postsbrowser.mobile.ui.main.MainActivityIntent
import com.sumitanantwar.postsbrowser.mobile.util.AnimatorListenerAdapter
import timber.log.Timber

class SplashActivity : BaseActivity() {

    @BindView(R.id.splash_animation_view)
    lateinit var splashAnimationView: LottieAnimationView


    // ======= Activity Lifecycle =======
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)

        splashAnimationView.addAnimatorListener(AnimatorListenerAdapter(
            onEnd = {
                Timber.d("Animation End")
                launchMainActivity()
            }
        ))
    }

    override fun onStart() {
        super.onStart()

        splashAnimationView.playAnimation()
    }

    private fun launchMainActivity() {
        val intent = MainActivityIntent()
        this.startActivity(intent)
    }
}