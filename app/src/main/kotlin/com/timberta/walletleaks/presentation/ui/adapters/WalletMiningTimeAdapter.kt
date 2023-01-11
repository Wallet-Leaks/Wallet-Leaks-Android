package com.timberta.walletleaks.presentation.ui.adapters

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.timberta.walletleaks.R
import com.timberta.walletleaks.databinding.ItemWalletMiningTimeBinding
import com.timberta.walletleaks.presentation.base.BaseDiffUtil
import com.timberta.walletleaks.presentation.extensions.loge
import com.timberta.walletleaks.presentation.models.WalletMiningTimeUI
import java.util.concurrent.TimeUnit

class WalletMiningTimeAdapter(
    private val doesUserHavePremium: Boolean,
    private val onItemClick: (amountOfHours: Long, isPremiumSupported: Boolean, position: Int) -> Unit,
    private var selectedTimeToMine: Long,
    private var miningAvailability: Boolean
) :
    ListAdapter<WalletMiningTimeUI, WalletMiningTimeAdapter.WalletMiningTimeViewHolder>(BaseDiffUtil()) {

    private var lastlySelectedPosition = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = WalletMiningTimeViewHolder(
        ItemWalletMiningTimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: WalletMiningTimeViewHolder, position: Int) {
        getItem(position)?.let { holder.onBind(it) }
    }

    private fun createWalletMiningTimeConstraints() =
        submitList(
            listOf(
                WalletMiningTimeUI(1, 10000),
                WalletMiningTimeUI(2, 20000),
                WalletMiningTimeUI(3, 30000),
                WalletMiningTimeUI(4, 40000, doesUserHavePremium)
            )
        )

    fun modifyMining(_miningTimePauseTimer: Long, _miningAvailability: Boolean) {
        selectedTimeToMine = _miningTimePauseTimer
        miningAvailability = _miningAvailability
    }

    inner class WalletMiningTimeViewHolder(private val binding: ItemWalletMiningTimeBinding) :
        ViewHolder(binding.root) {

        fun onBind(time: WalletMiningTimeUI) = with(binding) {
            imSelectedTime.isSelected = lastlySelectedPosition == absoluteAdapterPosition
            tvWalletMineTime.text = TimeUnit.MILLISECONDS.toHours(time.time).toString()
            if (!time.isUnlocked) {
                imSelectedTime.setImageResource(R.drawable.ic_timer_premium)
                tvWalletMineTime.apply {
                    val paint = paint
                    val width = paint.measureText(text.toString())
                    val textShader: Shader = LinearGradient(
                        0f, 0f, width, textSize, intArrayOf(
                            Color.parseColor("#FF0075"),
                            Color.parseColor("#E900FF")
                        ), null, Shader.TileMode.REPEAT
                    )
                    this.paint.shader = textShader
                }
            }
            loge(selectedTimeToMine.toString())
            loge(miningAvailability.toString())
        }

        init {
            binding.root.setOnClickListener {
                if (selectedTimeToMine.toInt() == 0 && !miningAvailability) {
                    if (lastlySelectedPosition >= 0)
                        notifyItemChanged(lastlySelectedPosition)
                    lastlySelectedPosition = absoluteAdapterPosition
                    notifyItemChanged(lastlySelectedPosition)
                    onItemClick(
                        getItem(absoluteAdapterPosition).time,
                        getItem(absoluteAdapterPosition).isUnlocked,
                        absoluteAdapterPosition
                    )
                }
            }
        }
    }

    init {
        createWalletMiningTimeConstraints()
    }
}