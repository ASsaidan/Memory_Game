package com.example.firstapp

import android.graphics.Color
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Game(
    private val activity: AppCompatActivity,
    private val imageViews: Array<ImageView?>,
    private val tv_p1: TextView,
    private val tv_p2: TextView,
) {

    private val players: Array<Player> get() = arrayOf(player1, player2)
    private val player1 = Player()
    private val player2 = Player()
    private var turn = 0 // Variable to track the current player's turn
    private var firstCard = 0 // Index of the first revealed card
    private var secondCard = 0 // Index of the second revealed card
    private var clickedFirst = 0 // Tag of the first clicked card
    private var clickedSecond = 0 // Tag of the second clicked card
    private var cardNumber = 1 // Counter to keep track of revealed cards

    private val imagePairs = arrayOf(
        Pair(R.drawable.image1, R.drawable.image11),
        Pair(R.drawable.image2, R.drawable.image22),
        Pair(R.drawable.image3, R.drawable.image33),
        Pair(R.drawable.image4, R.drawable.image44),
        Pair(R.drawable.image5, R.drawable.image55),
        Pair(R.drawable.image6, R.drawable.image66),
        Pair(R.drawable.image8, R.drawable.image88),
        Pair(R.drawable.image9, R.drawable.image99),
        Pair(R.drawable.image10, R.drawable.image100),
        Pair(R.drawable.image13, R.drawable.image133)
    )

    fun initializeVariables() {

        // Shuffle
        imagePairs.shuffle()

        // Set shuffled hidden images
        for (i in imageViews.indices) {
            imageViews[i]?.setImageResource(R.drawable.image7)
        }

    }


    fun revealCard(iv: ImageView, card: Int) {
        val validCard = card % imagePairs.size
        val cardPair = imagePairs[validCard]
        val imageResource = if (validCard % 2 == 0) cardPair.first else cardPair.second
        iv.setImageResource(imageResource)
        iv.isEnabled = false

        if (cardNumber == 1) {
            firstCard = validCard
            cardNumber = 2
            clickedFirst = card
        } else if (cardNumber == 2) {
            secondCard = validCard
            cardNumber = 1
            clickedSecond = card
            disableAllImageViews()
            val handler = Handler()
            handler.postDelayed({
                calculate()
            }, 1000)
        }

        tv_p1.text = "Player 1: ${player1.playerPoints}"
        tv_p2.text = "Player 2: ${player2.playerPoints}"
    }

    private fun calculate() {  // Match logic


        if (firstCard % 100 == secondCard % 100) {


            imageViews[clickedFirst]?.visibility = View.INVISIBLE
            imageViews[clickedSecond]?.visibility = View.INVISIBLE

            players[turn].scoreIncrease()

            tv_p1.text = "Player 1: ${players[0].playerPoints}"
            tv_p2.text = "Player 2: ${players[1].playerPoints}"

            val handler = Handler()
            handler.postDelayed({
                clickedFirst = 0
                clickedSecond = 0
                changeTurn()
                enableAllImageViews()
            }, 500)

        } else {

            for (imageView in imageViews) {
                imageView?.setImageResource(R.drawable.image7)
            }

            for (imageView in imageViews) {
                imageView?.isEnabled = true
            }

            clickedFirst = 0
            clickedSecond = 0

            changeTurn()
        }


        checkEnd()
    }


    private fun disableAllImageViews() {
        for (imageView in imageViews) {
            imageView?.isEnabled = false
        }
    }


    fun setClickListeners() {
        for (imageView in imageViews) {
            imageView?.setOnClickListener { view ->
                val theCard = (view?.tag as String).toInt()
                revealCard(view as ImageView, theCard)
            }
        }
    }

    private fun checkEnd() {
        val allCardsInvisible = imageViews.all { it?.visibility == View.INVISIBLE }
        if (allCardsInvisible) {

            (activity as MainActivity).showEndDialog(getEndMessage())
        }
    }

    private fun changeTurn() {

        turn = 1 - turn

        if (turn == 0) {
            tv_p1.setTextColor(Color.BLACK)
            tv_p2.setTextColor(Color.GRAY)
        } else {
            tv_p1.setTextColor(Color.GRAY)
            tv_p2.setTextColor(Color.BLACK)
        }
    }

    private fun enableAllImageViews() {
        for (imageView in imageViews) {
            if (imageView?.visibility != View.INVISIBLE) {
                if (imageView != null) {
                    imageView.isEnabled = true
                }
            }
        }
    }

    private fun getEndMessage(): String {
        return if (player1Won()) {
            "Player 1 Won!"
        } else if (player2Won()) {
            "Player 2 Won!"
        } else {
            "It's a Draw!"
        }
    }

    private fun player1Won() = player1.playerPoints > player2.playerPoints

    private fun player2Won() = player1.playerPoints < player2.playerPoints


}