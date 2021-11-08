package com.thana.simplegame.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.thana.simplegame.R
import com.thana.simplegame.databinding.FragmentLevelFiveBinding
import com.thana.simplegame.ui.common.BaseFragment
import com.thana.simplegame.ui.common.viewBinding


class LevelFiveFragment : BaseFragment(R.layout.fragment_level_five), View.OnTouchListener,
    View.OnDragListener {

    private val binding by viewBinding(FragmentLevelFiveBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()

    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setListeners() {

        binding.blueBall.setOnTouchListener(this)
        binding.redBall.setOnTouchListener(this)
        binding.placeholder1.setOnTouchListener(this)
        binding.placeholder2.setOnTouchListener(this)
        binding.placeholder3.setOnTouchListener(this)
        binding.placeholder4.setOnTouchListener(this)
        binding.area.setOnDragListener(this)

    }

    private fun checkIfMixed(dragEvent: DragEvent, view: View) {

        val blueBallXStart = binding.blueBall.x
        val blueBallYStart = binding.blueBall.y

        val blueBallXEnd = blueBallXStart + binding.blueBall.width
        val blueBallYEnd = blueBallYStart + binding.blueBall.height

        val redBallXStart = binding.redBall.x
        val redBallYStart = binding.redBall.y

        val redBallXEnd = redBallXStart + binding.redBall.width
        val redBallYEnd = redBallYStart + binding.redBall.height


        if (view.id == binding.redBall.id || view.id == binding.blueBall.id) {
            if (dragEvent.x in blueBallXStart..blueBallXEnd
                && dragEvent.y in blueBallYStart..blueBallYEnd
                && dragEvent.x in redBallXStart..redBallXEnd
                && dragEvent.y in redBallYStart..redBallYEnd
            ) {
                binding.redBall.glowColor= R.color.purple
                binding.redBall.backColor= R.color.purple
                binding.blueBall.visibility = View.INVISIBLE
                binding.right.visibility = View.VISIBLE
            }
        }


    }

    override fun onDrag(layoutview: View, dragevent: DragEvent): Boolean {

        val view = dragevent.localState as View

        when (dragevent.action) {

            DragEvent.ACTION_DRAG_ENTERED -> {
                view.alpha = 0.3f
            }
            DragEvent.ACTION_DROP -> {
                view.alpha = 1.0f
                val owner = binding.area
                owner.removeView(view)

                val container = layoutview as ConstraintLayout

                view.x = dragevent.x - (view.width / 2)
                view.y = dragevent.y - (view.height / 2)
                container.addView(view)
                view.visibility = View.VISIBLE
                checkIfMixed(dragevent, view)

            }

        }
        return true
    }


    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {

        return if (motionEvent.action == MotionEvent.ACTION_DOWN) {

            view.performClick()
            val shadowBuilder = View.DragShadowBuilder(view)

            view.startDragAndDrop(null, shadowBuilder, view, 0)

            view.visibility = View.INVISIBLE
            true
        } else {
            false
        }
    }
}
