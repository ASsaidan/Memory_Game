package com.example.firstapp

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class StartActivity : AppCompatActivity() {
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_page)

        val startButton: Button = findViewById(R.id.startButton)
        startButton.background = ContextCompat.getDrawable(this, R.drawable.press_elevation)

        val animator = AnimatorInflater.loadAnimator(this, R.animator.button_hover) as AnimatorSet
        startButton.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    animator.setTarget(v)
                    animator.start()
                }

                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    animator.cancel()
                }
            }
            false
        }

        startButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}