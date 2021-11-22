package com.thana.simplegame

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.thana.simplegame.databinding.FragmentLevelThirteenBinding
import com.thana.simplegame.ui.SharedViewModel
import com.thana.simplegame.ui.common.BaseFragment
import com.thana.simplegame.ui.common.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LevelThirteenFragment : BaseFragment(R.layout.fragment_level_thirteen), View.OnTouchListener,
    View.OnDragListener {

    private val binding by viewBinding(FragmentLevelThirteenBinding::bind)

    private val viewModel: SharedViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()

        binding.next.setOnClickListener {
            nextLevel()
        }

        showHint()
    }

    private fun showHint() = if (viewModel.isExpanded) {
        binding.hint.visibility = View.VISIBLE
        binding.expand.setIconResource(R.drawable.ic_collapse_arrow)
    } else {
        binding.hint.visibility = View.GONE
        binding.expand.setIconResource(R.drawable.ic_expand_arrow)
    }

    private fun nextLevel() {

        val action = LevelThirteenFragmentDirections.actionLevelThirteenFragmentToLevelFourteenFragment()
        findNavController().navigate(action)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setListeners() {
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


    override fun onDrag(layoutview: View, dragevent: DragEvent): Boolean {

        val view = dragevent.localState as View

        when (dragevent.action) {

            DragEvent.ACTION_DRAG_ENTERED -> {
                view.visibility = View.INVISIBLE
            }

            DragEvent.ACTION_DROP -> {
                view.alpha = 1.0f

                validateAnswer(dragevent)

                try {
                    if (!wrongArea(dragevent)) {
                        val owner = view.parent as ViewGroup
                        owner.removeView(view)
                        val container = layoutview as ConstraintLayout
                        view.x = dragevent.x - (view.width / 2)
                        view.y = dragevent.y - (view.height / 2)
                        container.addView(view)
                    }
                } catch (e: Exception) {
                    Log.d("Draging", "Something went wrong")
                }

                view.visibility = View.VISIBLE
            }
            DragEvent.ACTION_DRAG_EXITED -> {
                view.alpha = 1.0f
                view.visibility = View.VISIBLE
            }

        }
        return true
    }


    private fun validateAnswer(dragEvent: DragEvent) {

        val fireXStart = binding.fireText.x
        val fireYStart = binding.fireText.y

        val faceXEnd = fireXStart + binding.fireText.width
        val faceYEnd = fireYStart + binding.fireText.height

        if (dragEvent.x in fireXStart..faceXEnd && dragEvent.y in fireYStart..faceYEnd) {
            binding.right.visibility = View.VISIBLE
            binding.next.visibility = View.VISIBLE
            binding.celebrate.visibility = View.VISIBLE
            binding.celebrate.playAnimation()
            binding.fire.visibility = View.VISIBLE
            viewModel.playWin()

            viewModel.addScore(levelNumber = 13)
        }

    }

    private fun wrongArea(dragEvent: DragEvent): Boolean {
        val areaXStart = binding.untouchable.x
        val areaYStart = binding.untouchable.y

        val areaXEnd = areaXStart + binding.untouchable.width
        val areaYEnd = areaYStart + binding.untouchable.height

        if (dragEvent.x in areaXStart..areaXEnd && dragEvent.y in areaYStart..areaYEnd) {
            return true
        }

        return false
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
