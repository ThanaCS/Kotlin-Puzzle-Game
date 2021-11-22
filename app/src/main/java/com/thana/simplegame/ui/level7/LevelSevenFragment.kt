package com.thana.simplegame.ui.level7

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.thana.simplegame.R
import com.thana.simplegame.databinding.FragmentLevelSevenBinding
import com.thana.simplegame.ui.SharedViewModel
import com.thana.simplegame.ui.common.BaseFragment
import com.thana.simplegame.ui.common.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LevelSevenFragment : BaseFragment(R.layout.fragment_level_seven), View.OnTouchListener,
    View.OnDragListener {

    private val binding by viewBinding(FragmentLevelSevenBinding::bind)
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
        val action = LevelSevenFragmentDirections.actionLevelSevenFragmentToLevelEightFragment()
        findNavController().navigate(action)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setListeners() {
        binding.blue.setOnTouchListener(this)
        binding.yellow.setOnTouchListener(this)
        binding.ball1.setOnTouchListener(this)
        binding.ball2.setOnTouchListener(this)
        binding.yellowBall.setOnTouchListener(this)
        binding.ball4.setOnTouchListener(this)
        binding.ball5.setOnTouchListener(this)
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

        val correctAreaXStart = binding.correctArea.x
        val correctAreaYStart = binding.correctArea.y

        val correctAreaXEnd = correctAreaXStart + binding.correctArea.width
        val correctAreaYEnd = correctAreaYStart + binding.correctArea.height

        val yellowBallXStart = binding.yellowBall.x
        val yellowBallYStart = binding.yellowBall.y

        val yellowBallXEnd = yellowBallXStart + binding.yellowBall.width
        val yellowBallYEnd = yellowBallYStart + binding.yellowBall.height


        if (view.id == binding.correctArea.id ||
            view.id == binding.yellowBall.id
        ) {
            if (dragEvent.x in correctAreaXStart..correctAreaXEnd
                && dragEvent.y in correctAreaYStart..correctAreaYEnd
                && dragEvent.x in yellowBallXStart..yellowBallXEnd
                && dragEvent.y in yellowBallYStart..yellowBallYEnd

            ) {

                correctAnswer(view as ImageView)

            }
        }


    }

    private fun correctAnswer(view: ImageView) {
        view.visibility = View.VISIBLE
        view.setColorFilter(
            ContextCompat.getColor(requireContext(), R.color.green),
            android.graphics.PorterDuff.Mode.SRC_IN
        )
        binding.next.visibility = View.VISIBLE
        binding.celebrate.visibility = View.VISIBLE
        binding.right.visibility = View.VISIBLE
        binding.celebrate.playAnimation()
        viewModel.playWin()

        viewModel.addScore(levelNumber = 7)

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
