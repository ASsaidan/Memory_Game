package com.example.firstapp

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var imageViews: Array<ImageView?>
    private lateinit var game: Game
    private lateinit var tv_p1: TextView
    private lateinit var tv_p2: TextView


    override fun onCreate(outState: Bundle?) {
        super.onCreate(outState)
        overridePendingTransition(0, 0)
        setContentView(R.layout.activity_main)


        imageViews = arrayOf(
            findViewById(R.id.iv_1),
            findViewById(R.id.iv_2),
            findViewById(R.id.iv_3),
            findViewById(R.id.iv_4),
            findViewById(R.id.iv_5),
            findViewById(R.id.iv_6),
            findViewById(R.id.iv_7),
            findViewById(R.id.iv_8),
            findViewById(R.id.iv_9),
            findViewById(R.id.iv_10),
            findViewById(R.id.iv_11),
            findViewById(R.id.iv_12),
            findViewById(R.id.iv_13),
            findViewById(R.id.iv_14),
            findViewById(R.id.iv_15),
            findViewById(R.id.iv_16),
            findViewById(R.id.iv_17),
            findViewById(R.id.iv_18),
            findViewById(R.id.iv_19),
            findViewById(R.id.iv_20)


        )



        initializeViews()
        game = Game(this, imageViews, tv_p1, tv_p2)
        game.initializeVariables()
        game.setClickListeners()
    }


    fun initializeViews() {
        tv_p1 = findViewById(R.id.textView2)
        tv_p2 = findViewById(R.id.textView3)
        imageViews.forEach {
            it?.setImageResource(R.drawable.image7)
        }




        for ((index, imageView) in imageViews.withIndex()) {
            imageView?.tag = index.toString()
        }


    }

    fun showEndDialog(title: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setPositiveButton("Play Again") { _, _ ->
                finish()
                game.initializeVariables()
                game.setClickListeners()
            }
            .setNegativeButton("Cancel") { _, _ ->
                finish()
            }
            .show()
    }


}

