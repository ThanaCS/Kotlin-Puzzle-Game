package com.thana.simplegame.ui.level14

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.thana.simplegame.R
import com.thana.simplegame.databinding.FragmentLevelFourteenBinding
import com.thana.simplegame.ui.SharedViewModel
import com.thana.simplegame.ui.common.BaseFragment
import com.thana.simplegame.ui.common.viewBinding
import com.thana.simplegame.ui.level4.LevelFourFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LevelFourteenFragment : BaseFragment(R.layout.fragment_level_fourteen), View.OnTouchListener,
    View.OnDragListener {

    private val binding by viewBinding(FragmentLevelFourteenBinding::bind)
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
        val action =
            LevelFourteenFragmentDirections.actionLevelFourteenFragmentToLevelFifteenFragment()
        findNavController().navigate(action)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setListeners() {
        binding.screw.setOnTouchListener(this)
        binding.screw2.setOnTouchListener(this)
        binding.screw3.setOnTouchListener(this)
        binding.screwDriver.setOnTouchListener(this)
        binding.wire.setOnTouchListener(this)
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

    private fun checkIfPlugged(dragEvent: DragEvent, view: View) {

        val wireXStart = binding.wire.x
        val wireYStart = binding.wire.y

        val wireXEnd = wireXStart + binding.wire.width
        val wireYEnd = wireYStart + binding.wire.height

        val correctAreaXStart = binding.correctArea.x
        val correctAreaYStart = binding.correctArea.y

        val correctAreaXEnd = correctAreaXStart + binding.correctArea.width
        val correctAreaYEnd = correctAreaYStart + binding.correctArea.height


        if (view.id == binding.wire.id || view.id == binding.correctArea.id) {
            if (dragEvent.x in wireXStart..wireXEnd
                && dragEvent.y in wireYStart..wireYEnd
                && dragEvent.x in correctAreaXStart..correctAreaXEnd
                && dragEvent.y in correctAreaYStart..correctAreaYEnd
            ) {
                correctAnswer()
            }
        }
    }


    private fun correctAnswer() {
        binding.right.visibility = View.VISIBLE
        binding.next.visibility = View.VISIBLE
        binding.celebrate.visibility = View.VISIBLE
        binding.plug.visibility = View.VISIBLE
        binding.wire.visibility = View.GONE
        binding.celebrate.playAnimation()
        viewModel.playWin()

        viewModel.addScore(levelNumber = 14)

    }

    override fun onDrag(layoutview: View, dragevent: DragEvent): Boolean {

        val view = dragevent.localState as View

        when (dragevent.action) {

            DragEvent.ACTION_DRAG_ENTERED -> {
                view.alpha = 0.3f
                view.visibility = View.INVISIBLE

            }
            DragEvent.ACTION_DRAG_STARTED -> {
                view.rotation = 90f
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
                checkIfPlugged(dragevent, view)

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