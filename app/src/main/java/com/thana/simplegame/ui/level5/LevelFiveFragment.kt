package com.thana.simplegame.ui.level5

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.thana.simplegame.R
import com.thana.simplegame.databinding.FragmentLevelFiveBinding
import com.thana.simplegame.ui.SharedViewModel
import com.thana.simplegame.ui.common.BaseFragment
import com.thana.simplegame.ui.common.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LevelFiveFragment : BaseFragment(R.layout.fragment_level_five), View.OnTouchListener,
    View.OnDragListener {

    private val binding by viewBinding(FragmentLevelFiveBinding::bind)
    private val viewModel: SharedViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
        showHint()
        binding.next.setOnClickListener {
            nextLevel()
        }
    }

    private fun showHint() = if (viewModel.isExpanded) {
        binding.hint.visibility = View.VISIBLE
        binding.expand.setIconResource(R.drawable.ic_collapse_arrow)
    } else {
        binding.hint.visibility = View.GONE
        binding.expand.setIconResource(R.drawable.ic_expand_arrow)
    }

    private fun nextLevel() {
        val action = LevelFiveFragmentDirections.actionLevelFiveFragmentToLevelSixFragment()
        findNavController().navigate(action)
    }


    @SuppressLint("ClickableViewAccessibility")
    private fun setListeners() {

        binding.cow.setOnTouchListener(this)
        binding.eggs.setOnTouchListener(this)
        binding.food1.setOnTouchListener(this)
        binding.food2.setOnTouchListener(this)
        binding.flour.setOnTouchListener(this)
        binding.area.setOnDragListener(this)
        binding.hintRoot.setOnClickListener {
            viewModel.isExpanded = !viewModel.isExpanded
            showHint()
        }
        binding.expand.setOnClickListener {
            viewModel.isExpanded = !viewModel.isExpanded
            showHint()
        }
    }

    private fun checkIfMixed(dragEvent: DragEvent, view: View) {

        val cowXStart = binding.cow.x
        val cowYStart = binding.cow.y

        val cowXEnd = cowXStart + binding.cow.width
        val cowYEnd = cowYStart + binding.cow.height

        val flourXStart = binding.flour.x
        val flourYStart = binding.flour.y

        val flourXEnd = flourXStart + binding.flour.width
        val flourYEnd = flourYStart + binding.flour.height

        val eggXStart = binding.eggs.x
        val eggYStart = binding.eggs.y

        val eggXEnd = eggXStart + binding.eggs.width
        val eggYEnd = eggYStart + binding.eggs.height


        if (view.id == binding.cow.id || view.id == binding.flour.id || view.id == binding.eggs.id) {
            if (dragEvent.x in cowXStart..cowXEnd
                && dragEvent.y in cowYStart..cowYEnd
                && dragEvent.x in flourXStart..flourXEnd
                && dragEvent.y in flourYStart..flourYEnd
                && dragEvent.x in flourXStart..eggXEnd
                && dragEvent.y in flourYStart..eggYEnd
            ) {

                correctAnswer()

            }
        }


    }


    private fun correctAnswer() {

        binding.pancake.visibility = View.VISIBLE
        binding.food1.visibility = View.INVISIBLE
        binding.food2.visibility = View.INVISIBLE
        binding.cow.visibility = View.INVISIBLE
        binding.eggs.visibility = View.INVISIBLE
        binding.flour.visibility = View.INVISIBLE
        binding.right.visibility = View.VISIBLE
        binding.next.visibility = View.VISIBLE
        binding.celebrate.visibility = View.VISIBLE
        binding.celebrate.playAnimation()
        viewModel.playWin()

        viewModel.addScore(levelNumber = 5)

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

                val container = layoutview as ConstraintLayout

                view.x = dragevent.x - (view.width / 2)
                view.y = dragevent.y - (view.height / 2)
                container.addView(view)
                view.visibility = View.VISIBLE
                checkIfMixed(dragevent, view)

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
