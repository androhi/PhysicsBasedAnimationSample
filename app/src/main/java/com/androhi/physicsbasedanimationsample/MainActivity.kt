package com.androhi.physicsbasedanimationsample

import android.os.Bundle
import android.support.animation.DynamicAnimation
import android.support.animation.SpringAnimation
import android.support.animation.SpringForce
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioGroup

class MainActivity : AppCompatActivity() {

    private val dumpGroup by lazy {
        findViewById<RadioGroup>(R.id.dumping_ratio_group)
    }

    private val stiffnessGroup by lazy {
        findViewById<RadioGroup>(R.id.stiffness_group)
    }

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
            scaleAnimationX.spring = createSpringForce()
            scaleAnimationY.spring = createSpringForce()
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
        dampingRatio = when (dumpGroup.checkedRadioButtonId) {
            R.id.dumping_ratio_high -> SpringForce.DAMPING_RATIO_HIGH_BOUNCY
            R.id.dumping_ratio_medium -> SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
            R.id.dumping_ratio_low -> SpringForce.DAMPING_RATIO_LOW_BOUNCY
            else -> SpringForce.DAMPING_RATIO_NO_BOUNCY
        }
        // 剛性を設定
        stiffness = when (stiffnessGroup.checkedRadioButtonId) {
            R.id.stiffness_high -> SpringForce.STIFFNESS_HIGH
            R.id.stiffness_medium -> SpringForce.STIFFNESS_MEDIUM
            R.id.stiffness_low -> SpringForce.STIFFNESS_LOW
            else -> SpringForce.STIFFNESS_VERY_LOW
        }
    }

    private fun startAnimation() {
        // setFinalPosition()とstart()を一緒に呼んでくれる
        scaleAnimationX.animateToFinalPosition(FINAL_SCALE)
        scaleAnimationY.animateToFinalPosition(FINAL_SCALE)
    }

    private fun resetScaleAndAnimation() {
        dumpGroup.check(R.id.dumping_ratio_medium)
        stiffnessGroup.check(R.id.stiffness_medium)
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
