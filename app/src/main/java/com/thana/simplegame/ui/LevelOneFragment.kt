package com.thana.simplegame.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController
import com.thana.simplegame.R
import com.thana.simplegame.databinding.FragmentLevelOneBinding
import com.thana.simplegame.ui.common.BaseFragment
import com.thana.simplegame.ui.common.viewBinding

class LevelOneFragment : BaseFragment(R.layout.fragment_level_one), View.OnTouchListener,
    View.OnDragListener {

    private val binding by viewBinding(FragmentLevelOneBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        validateAnswer()

        binding.next.setOnClickListener {
            nextLevel()
        }

    }

    private fun nextLevel() {
        val action = LevelOneFragmentDirections.actionLevelOneFragmentToLevelTwoFragment()
        findNavController().navigate(action)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setListeners() {
        binding.pizza1.setOnTouchListener(this)
        binding.pizza2.setOnTouchListener(this)
        binding.hiddenPizza.setOnTouchListener(this)
        binding.pizza4.setOnTouchListener(this)
        binding.pizza5.setOnTouchListener(this)
        binding.pizza6.setOnTouchListener(this)
        binding.area.setOnDragListener(this)
        binding.input.setOnDragListener { _, _ ->

            return@setOnDragListener true
        }
        binding.editText.setOnDragListener { _, _ ->

            return@setOnDragListener true
        }
    }

    private fun validateAnswer() {
        binding.submit.setOnClickListener {
            val input = binding.editText.text.toString().trim()
            if (input == "7") {
                binding.right.visibility = View.VISIBLE
                binding.wrong.visibility = View.GONE
            } else {
                binding.wrong.visibility = View.VISIBLE
                binding.right.visibility = View.GONE
            }
        }
        binding.right.setOnClickListener {
            binding.right.visibility = View.GONE
        }

        binding.wrong.setOnClickListener {
            binding.wrong.visibility = View.GONE
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