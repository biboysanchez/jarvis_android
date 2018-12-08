package com.jarvis.app.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.jarvis.app.R
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_splash.*

import android.support.v4.view.ViewCompat
import android.support.v4.app.ActivityOptionsCompat
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener



class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        hideStatusBar()

        startFadeInAnimation()
    }
    private fun hideStatusBar() {
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    private fun startFadeInAnimation() {
        val startAnimation = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in)
        startAnimation.setAnimationListener(object : AnimationListener {
            override fun onAnimationStart(anim: Animation) {}
            override fun onAnimationRepeat(anim: Animation) {}
            override fun onAnimationEnd(anim: Animation) {

                val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this@SplashActivity,
                    img_logo,
                    ViewCompat.getTransitionName(img_logo)!!
                )
                startActivity(intent, options.toBundle())

                Handler().postDelayed({
                    this@SplashActivity.finish()
                },3000)
            }
        })
        img_logo?.startAnimation(startAnimation)
    }
}
