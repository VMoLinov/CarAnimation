package ru.molinov.caranimation

import kotlin.math.absoluteValue
import kotlin.random.Random

class MainPresenter(private val mainView: MainView) {

    fun moveX() {
        val x = mainView.calculateX()
        if (x <= MIN_X) mainView.moveX(x)
        else {
            val random = Random.nextInt(MIN_X.toInt(), x.toInt().absoluteValue)
            mainView.moveX(random.toFloat())
        }
    }

    fun moveY() {
        val y = mainView.calculateY()
        if (y.absoluteValue <= MIN_Y) mainView.moveY(y)
        else {
            val random = Random.nextInt(MIN_Y.toInt(), y.absoluteValue.toInt())
            mainView.moveY(random.toFloat())
        }
    }

    companion object {
        const val MIN_X = 20f
        const val MIN_Y = 40f
    }
}
