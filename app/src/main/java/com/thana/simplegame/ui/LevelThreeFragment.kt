package com.thana.simplegame.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.thana.simplegame.R
import com.thana.simplegame.databinding.FragmentLevelThreeBinding
import com.thana.simplegame.ui.common.BaseFragment
import com.thana.simplegame.ui.common.viewBinding


class LevelThreeFragment : BaseFragment(R.layout.fragment_level_three), View.OnTouchListener,
    View.OnDragListener {

    private val binding by viewBinding(FragmentLevelThreeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()

    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setListeners() {
        binding.swipe.setOnTouchListener(this)
        binding.area.setOnDragListener(this)

    }

    private fun checkIfOnRightArea(dragEvent: DragEvent) {
        val faceXStart = binding.rightArea.x
        val faceYStart = binding.rightArea.y

        val faceXEnd = faceXStart + binding.rightArea.width
        val faceYEnd = faceYStart + binding.rightArea.height

        if (dragEvent.x in faceXStart..faceXEnd && dragEvent.y in faceYStart..faceYEnd) {
            binding.right.visibility = View.VISIBLE
            binding.wrong.visibility = View.GONE
        }

        binding.swipe.setOnClickListener {
            binding.right.visibility = View.GONE
            binding.wrong.visibility = View.GONE
        }

    }

    private fun checkIfOnWrongArea(dragEvent: DragEvent) {
        val faceXStart = binding.wrongArea.x
        val faceYStart = binding.wrongArea.y

        val faceXEnd = faceXStart + binding.wrongArea.width
        val faceYEnd = faceYStart + binding.wrongArea.height

        if (dragEvent.x in faceXStart..faceXEnd && dragEvent.y in faceYStart..faceYEnd) {
            binding.wrong.visibility = View.VISIBLE
            binding.right.visibility = View.GONE
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
                val owner = view.parent as ViewGroup
                owner.removeView(view)

                val container = binding.area

                view.x = dragevent.x - (view.width / 2)
                view.y = dragevent.y - (view.height / 2)

                checkIfOnRightArea(dragevent)
                checkIfOnWrongArea(dragevent)
                container.addView(view)
                view.visibility = View.VISIBLE
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
