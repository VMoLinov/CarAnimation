package ru.molinov.caranimation

import android.animation.ObjectAnimator
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.view.isVisible
import ru.molinov.caranimation.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity(), MainView {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val presenter: MainPresenter by lazy { MainPresenter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.apply {
            car.setOnClickListener {
                it.isClickable = false
                animateHandler()
            }
            restart.setOnClickListener {
                car.translationX = ZERO
                car.translationY = ZERO
                car.rotation = ZERO
                car.isClickable = true
                restart.isVisible = false
            }
        }
    }

    private fun animateHandler() {
        when (Random.nextInt(DIRECTIONS)) {
            0 -> {
                if (calculateX() != ZERO) presenter.moveX()
                else if (calculateY() != ZERO) animateHandler()
            }
            1 -> {
                if (calculateY() != ZERO) presenter.moveY()
                else if (calculateX() != ZERO) animateHandler()
            }
        }
        if (calculateX() == ZERO && calculateY() == ZERO) {
            binding.restart.isVisible = true
            toast("Finish")
        }
    }

    override fun moveX(x: Float) {
        if (binding.car.rotation == ROTATE_UP) {
            ObjectAnimator.ofFloat(binding.car, "rotation", ROTATE_UP, ROTATE_END).apply {
                duration = ROTATE_ANIMATION
                start()
            }
        }
        ObjectAnimator.ofFloat(
            binding.car,
            "translationX",
            binding.car.translationX + x
        ).apply {
            duration = MAIN_ANIMATION
            start()
            doOnEnd { animateHandler() }
        }
    }

    override fun moveY(y: Float) {
        if (binding.car.rotation == ROTATE_END) {
            ObjectAnimator.ofFloat(binding.car, "rotation", ROTATE_END, ROTATE_UP).apply {
                duration = ROTATE_ANIMATION
                start()
            }
        }
        ObjectAnimator.ofFloat(
            binding.car,
            "translationY",
            binding.car.translationY - y
        ).apply {
            duration = MAIN_ANIMATION
            start()
            doOnEnd { animateHandler() }
        }
    }

    override fun calculateX() = with(binding) { frame.width - car.translationX - car.width }
    override fun calculateY() = with(binding) { frame.height + car.translationY - car.height }
    override fun toast(text: String) =
        Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT).show()

    companion object {
        const val MAIN_ANIMATION = 500L
        const val ROTATE_ANIMATION = 200L
        const val ROTATE_END = 0f
        const val ROTATE_UP = -90f
        const val ZERO = 0f
        const val DIRECTIONS = 2
    }
}
