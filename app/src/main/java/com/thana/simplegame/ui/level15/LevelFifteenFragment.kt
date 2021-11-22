package com.thana.simplegame.ui.level15

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import com.thana.simplegame.R
import com.thana.simplegame.databinding.FragmentLevelFifteenBinding
import com.thana.simplegame.ui.SharedViewModel
import com.thana.simplegame.ui.common.BaseFragment
import com.thana.simplegame.ui.common.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LevelFifteenFragment : BaseFragment(R.layout.fragment_level_fifteen), View.OnTouchListener,
    View.OnDragListener {

    private val binding by viewBinding(FragmentLevelFifteenBinding::bind)
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

    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setListeners() {
        binding.lighter.setOnTouchListener(this)
        binding.tv.setOnTouchListener(this)
        binding.smallFire.setOnTouchListener(this)
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

    private fun lightUp(dragEvent: DragEvent, view: View) {

        val lighterAreaXStart = binding.lighter.x
        val lighterAreaYStart = binding.lighter.y

        val lighterAreaXEnd = lighterAreaXStart + binding.lighter.width
        val lighterAreaYEnd = lighterAreaYStart + binding.lighter.height

        val matchesAreaXStart = binding.matches.x
        val matchesAreaYStart = binding.matches.y

        val matchesAreaXEnd = lighterAreaXStart + binding.matches.width
        val matchesAreaYEnd = lighterAreaYStart + binding.matches.height

        if (view.id == binding.lighter.id ||
            view.id == binding.matches.id
        ) {
            if (dragEvent.x in lighterAreaXStart..lighterAreaXEnd
                && dragEvent.y in lighterAreaYStart..lighterAreaYEnd
                && dragEvent.x in matchesAreaXStart..matchesAreaXEnd
                && dragEvent.y in matchesAreaYStart..matchesAreaYEnd

            ) {
                binding.matches.visibility = View.GONE
                binding.smallFire.visibility = View.VISIBLE

            }
        }
    }

    private fun makePopcorn(dragEvent: DragEvent, view: View) {

        val smallFireAreaXStart = binding.smallFire.x
        val smallFireAreaYStart = binding.smallFire.y

        val smallFireAreaXEnd = smallFireAreaXStart + binding.smallFire.width
        val smallFireAreaYEnd = smallFireAreaYStart + binding.smallFire.height

        val cornAreaXStart = binding.corn.x
        val cornAreaYStart = binding.corn.y

        val cornAreaXEnd = cornAreaXStart + binding.corn.width
        val cornAreaYEnd = cornAreaYStart + binding.corn.height



        if (view.id == binding.smallFire.id ||
            view.id == binding.corn.id
        ) {
            if (dragEvent.x in smallFireAreaXStart..smallFireAreaXEnd
                && dragEvent.y in smallFireAreaYStart..smallFireAreaYEnd
                && dragEvent.x in cornAreaXStart..cornAreaXEnd
                && dragEvent.y in cornAreaYStart..cornAreaYEnd

            ) {

                correctAnswer()

            }
        }
    }

    private fun correctAnswer() {

        binding.lighter.visibility =View.GONE
        binding.popcorn.visibility =View.VISIBLE
        binding.corn.visibility =View.GONE
        binding.next.visibility = View.VISIBLE
        binding.celebrate.visibility = View.VISIBLE
        binding.right.visibility = View.VISIBLE
        binding.celebrate.playAnimation()
        viewModel.playWin()

        viewModel.addScore(levelNumber = 15)

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
                lightUp(dragevent, view)
                makePopcorn(dragevent, view)
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
