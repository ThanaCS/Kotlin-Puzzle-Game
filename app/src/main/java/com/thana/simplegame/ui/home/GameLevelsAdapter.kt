package com.thana.simplegame.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thana.simplegame.R
import com.thana.simplegame.data.model.GameInfo
import com.thana.simplegame.databinding.LevelItemBinding

class GameLevelsAdapter(private val clickListener: LevelClickListeners) :
    RecyclerView.Adapter<GameLevelsAdapter.LevelViewHolder>() {

    private val levelsList = mutableListOf<GameInfo>()

    fun setItems(items: List<GameInfo>) {
        this.levelsList.clear()
        this.levelsList.addAll(items)
        notifyDataSetChanged()
    }

    inner class LevelViewHolder(private val binding: LevelItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(gameInfo: GameInfo) {

            binding.apply {

                levelName.text =
                    "${binding.root.context.getString(R.string.level)} ${gameInfo.level}"

                if (clickListener.isLevelLocked(gameInfo.level)) {
                    levelIcon.setColorFilter(R.color.gray)
                } else {
                    root.setOnClickListener {

                        if (gameInfo.fragmentID != null)
                            clickListener.navigate(gameInfo.fragmentID)
                    }
                }


            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LevelViewHolder {
        val binding = LevelItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return LevelViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LevelViewHolder, position: Int) {
        val order = levelsList[position]
        holder.bind(order)
    }

    override fun getItemCount() = levelsList.size
}