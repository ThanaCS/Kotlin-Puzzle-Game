package com.thana.simplegame.ui.level6

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.upstream.RawResourceDataSource
import com.thana.simplegame.R.*
import com.thana.simplegame.databinding.FragmentLevelSixBinding
import com.thana.simplegame.ui.SharedViewModel
import com.thana.simplegame.ui.common.BaseFragment
import com.thana.simplegame.ui.common.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LevelSixFragment : BaseFragment(layout.fragment_level_six), View.OnTouchListener,
    View.OnDragListener {

    private val binding by viewBinding(FragmentLevelSixBinding::bind)
    private val viewModel: SharedViewModel by viewModels()

    private var isExpanded = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
        showHint()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setListeners() {

        binding.ball1.setOnTouchListener(this)
        binding.ball2.setOnTouchListener(this)
        binding.ball3.setOnTouchListener(this)
        binding.ball4.setOnTouchListener(this)
        binding.ball5.setOnTouchListener(this)
        binding.area.setOnDragListener(this)

    }

    private fun checkIfMixed(dragEvent: DragEvent, view: View) {

        val ball1XStart = binding.ball1.x
        val ball1YStart = binding.ball1.y

        val ballXEnd = ball1XStart + binding.ball1.width
        val ballYEnd = ball1YStart + binding.ball1.height

        val ball2XStart = binding.ball2.x
        val ball2YStart = binding.ball2.y

        val ball2XEnd = ball2XStart + binding.ball2.width
        val ball2YEnd = ball2YStart + binding.ball2.height

        val ball3XStart = binding.ball3.x
        val ball3YStart = binding.ball3.y

        val ball3XEnd = ball3XStart + binding.ball3.width
        val ball3YEnd = ball3YStart + binding.ball3.height

        val ball4XStart = binding.ball4.x
        val ball4YStart = binding.ball4.y

        val ball4XEnd = ball4XStart + binding.ball4.width
        val ball4YEnd = ball4YStart + binding.ball4.height

        val ball5XStart = binding.ball5.x
        val ball5YStart = binding.ball5.y

        val ball5XEnd = ball5XStart + binding.ball5.width
        val ball5YEnd = ball5YStart + binding.ball5.height

        if (view.id == binding.ball1.id ||
            view.id == binding.ball2.id ||
            view.id == binding.ball3.id ||
            view.id == binding.ball4.id ||
            view.id == binding.ball5.id
        ) {
            if (dragEvent.x in ball1XStart..ballXEnd
                && dragEvent.y in ball1YStart..ballYEnd
                && dragEvent.x in ball2XStart..ball2XEnd
                && dragEvent.y in ball2YStart..ball2YEnd
                && dragEvent.x in ball3XStart..ball3XEnd
                && dragEvent.y in ball3YStart..ball3YEnd
                && dragEvent.y in ball4YStart..ball4YEnd
                && dragEvent.x in ball4XStart..ball4XEnd
                && dragEvent.y in ball5YStart..ball5YEnd
                && dragEvent.x in ball5XStart..ball5XEnd

            ) {

                correctAnswer(view as ImageView)

            }
        }


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

    private fun correctAnswer(view: ImageView) {

        val winAudio = ExoPlayer.Builder(requireContext()).build()

        val winUri = RawResourceDataSource.buildRawResourceUri(raw.win)

        winAudio.apply {
            setMediaItem(MediaItem.fromUri(winUri))
            prepare()
        }

        binding.ball1.visibility = View.INVISIBLE
        binding.ball1.visibility = View.INVISIBLE
        binding.ball2.visibility = View.INVISIBLE
        binding.ball3.visibility = View.INVISIBLE
        binding.ball4.visibility = View.INVISIBLE
        binding.ball5.visibility = View.INVISIBLE
        binding.right.visibility = View.VISIBLE
        binding.next.visibility = View.VISIBLE
        view.visibility = View.VISIBLE
        view.setColorFilter(
            ContextCompat.getColor(requireContext(), color.brown),
            android.graphics.PorterDuff.Mode.SRC_IN
        )
        binding.celebrate.visibility = View.VISIBLE
        binding.celebrate.playAnimation()
        winAudio.play()

        if (viewModel.getScore() < 6) {
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
