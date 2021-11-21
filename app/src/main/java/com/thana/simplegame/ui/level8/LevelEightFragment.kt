package com.thana.simplegame.ui.level8

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.upstream.RawResourceDataSource
import com.thana.simplegame.R
import com.thana.simplegame.databinding.FragmentLevelEightBinding
import com.thana.simplegame.ui.SharedViewModel
import com.thana.simplegame.ui.common.BaseFragment
import com.thana.simplegame.ui.common.viewBinding
import com.thana.simplegame.ui.level7.LevelSevenFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LevelEightFragment : BaseFragment(R.layout.fragment_level_eight), View.OnTouchListener,
    View.OnDragListener {

    private val binding by viewBinding(FragmentLevelEightBinding::bind)
    private val viewModel: SharedViewModel by viewModels()

    private var isExpanded = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
        showHint()
        binding.title.setOnClickListener {
            correctAnswer()
        }
        binding.next.setOnClickListener {
            nextLevel()
        }

    }

    private fun nextLevel() {
        val action = LevelEightFragmentDirections.actionLevelEightFragmentToLevelNineFragment()
        findNavController().navigate(action)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setListeners() {
        binding.blue.setOnTouchListener(this)
        binding.yellow.setOnTouchListener(this)
        binding.ball2.setOnTouchListener(this)
        binding.yellowBall.setOnTouchListener(this)
        binding.ball4.setOnTouchListener(this)
        binding.ball5.setOnTouchListener(this)
        binding.area.setOnDragListener(this)

    }


    private fun showHint() {

        binding.hintRoot.setOnClickListener {
            if (isExpanded) expand() else collapse()
        }
        binding.expand.setOnClickListener {
            if (isExpanded) expand() else collapse()
        }
        binding.collapse.setOnClickListener {
            if (isExpanded) expand() else collapse()
        }
    }

    private fun expand() {

        binding.hint.visibility = View.VISIBLE
        binding.collapse.visibility = View.VISIBLE
        binding.expand.visibility = View.INVISIBLE
        isExpanded = false
    }

    private fun collapse() {
        binding.hint.visibility = View.GONE
        binding.collapse.visibility = View.INVISIBLE
        binding.expand.visibility = View.VISIBLE
        isExpanded = true

    }

    private fun correctAnswer() {

        binding.next.visibility = View.VISIBLE
        binding.right.visibility = View.VISIBLE
        binding.celebrate.visibility = View.VISIBLE
        binding.celebrate.playAnimation()
        viewModel.playWin()

        if (viewModel.getScore() < 8) {
            viewModel.addScore()
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

                val container = layoutview as ConstraintLayout

                view.x = dragevent.x - (view.width / 2)
                view.y = dragevent.y - (view.height / 2)

                container.addView(view)
                view.visibility = View.VISIBLE

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
