package com.thana.simplegame.ui.level3

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.thana.simplegame.R
import com.thana.simplegame.databinding.FragmentLevelThreeBinding
import com.thana.simplegame.ui.SharedViewModel
import com.thana.simplegame.ui.common.BaseFragment
import com.thana.simplegame.ui.common.viewBinding
import com.thana.simplegame.util.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LevelThreeFragment : BaseFragment(R.layout.fragment_level_three), View.OnTouchListener,
    View.OnDragListener {

    private val binding by viewBinding(FragmentLevelThreeBinding::bind)
    private val viewModel: SharedViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.next.setOnClickListener {
            nextLevel()
        }

        setListeners()
        showHint()
        validateAnswer()

    }

    private fun showHint() = if (viewModel.isExpanded) {
        binding.hint.visibility = View.VISIBLE
        binding.expand.setIconResource(R.drawable.ic_collapse_arrow)
    } else {
        binding.hint.visibility = View.GONE
        binding.expand.setIconResource(R.drawable.ic_expand_arrow)
    }

    private fun nextLevel() {
        val action = LevelThreeFragmentDirections.actionLevelThreeFragmentToLevelFourFragment()
        findNavController().navigate(action)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setListeners() {
        binding.cake1.setOnTouchListener(this)
        binding.cake2.setOnTouchListener(this)
        binding.cake3.setOnTouchListener(this)
        binding.area.setOnDragListener(this)
        binding.dinosaur.setOnDragListener(this)
        binding.input.setOnDragListener { _, _ ->

            return@setOnDragListener true
        }
        binding.editText.setOnDragListener { _, _ ->

            return@setOnDragListener true
        }
        binding.area2.setOnClickListener {
            viewModel.isExpanded = !viewModel.isExpanded
            showHint()
        }
        binding.expand.setOnClickListener {
            viewModel.isExpanded = !viewModel.isExpanded
            showHint()
        }
    }

    private fun validateAnswer() {

        binding.submit.setOnClickListener {

            val input = binding.editText.text.toString().trim()
            if (input == "1") {
                binding.right.visibility = View.VISIBLE
                binding.wrong.visibility = View.GONE
                binding.celebrate.visibility = View.VISIBLE
                binding.celebrate.playAnimation()
                binding.submit.isEnabled = false
                binding.next.visibility = View.VISIBLE

                viewModel.playWin()
                viewModel.addScore(levelNumber = 3)

            } else {
                binding.wrong.visibility = View.VISIBLE
                binding.right.visibility = View.GONE

                viewModel.playLose()
            }
            hideKeyboard()
        }
    }

    override fun onDrag(layoutview: View, dragevent: DragEvent): Boolean {

        val view = dragevent.localState as View

        when (dragevent.action) {

            DragEvent.ACTION_DRAG_ENTERED -> {
                view.alpha = 0.3f
                view.visibility = View.INVISIBLE
            }
            DragEvent.ACTION_DROP -> {
                view.alpha = 1.0f
                val owner = binding.area
                owner.removeView(view)

                if (layoutview.id != binding.dinosaur.id) {
                    val container = layoutview as ConstraintLayout

                    view.x = dragevent.x - (view.width / 2)
                    view.y = dragevent.y - (view.height / 2)

                    container.addView(view)
                    view.visibility = View.VISIBLE
                }
            }
            DragEvent.ACTION_DRAG_EXITED -> {
                view.alpha = 1.0f
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

            true
        } else {

            false
        }
    }
}

