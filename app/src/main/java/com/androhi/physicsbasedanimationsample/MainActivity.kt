package com.androhi.physicsbasedanimationsample

import android.os.Bundle
import android.support.animation.DynamicAnimation
import android.support.animation.SpringAnimation
import android.support.animation.SpringForce
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageView

class MainActivity : AppCompatActivity() {

    private val imageView by lazy {
        findViewById<ImageView>(R.id.image_view)
    }

    private val startButton by lazy {
        findViewById<Button>(R.id.start_button)
    }

    private val resetButton by lazy {
        findViewById<Button>(R.id.reset_button)
    }

    private val scaleAnimationX by lazy {
        createAnimation(SpringAnimation.SCALE_X)
    }

    private val scaleAnimationY by lazy {
        createAnimation(SpringAnimation.SCALE_Y)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startButton.setOnClickListener {
            startAnimation()
        }

        resetButton.setOnClickListener {
            resetScaleAndAnimation()
        }
    }

    private fun createAnimation(property: DynamicAnimation.ViewProperty): SpringAnimation {
        return SpringAnimation(imageView, property).apply {
            spring = createSpringForce()
        }
    }

    private fun createSpringForce() = SpringForce().apply {
        // 減衰率を設定
        dampingRatio = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
        // 剛性を設定
        stiffness = SpringForce.STIFFNESS_MEDIUM
    }

    private fun startAnimation() {
        // setFinalPosition()とstart()を一緒に呼んでくれる
        scaleAnimationX.animateToFinalPosition(FINAL_SCALE)
        scaleAnimationY.animateToFinalPosition(FINAL_SCALE)
    }

    private fun resetScaleAndAnimation() {
        scaleAnimationX.cancel()
        scaleAnimationY.cancel()
        imageView?.scaleX = INITIAL_SCALE
        imageView?.scaleY = INITIAL_SCALE
    }

    companion object {
        const val INITIAL_SCALE = 1f
        const val FINAL_SCALE = 3f
    }
}
