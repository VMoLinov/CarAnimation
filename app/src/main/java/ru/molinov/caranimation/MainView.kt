package ru.molinov.caranimation

interface MainView {

    fun moveX(x: Float)
    fun moveY(y: Float)
    fun calculateX(): Float
    fun calculateY(): Float
    fun toast(text: String)
}
