package com.example.mindicadorevaluation.features.detail.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mindicadorevaluation.core.models.Indicator
import com.example.mindicadorevaluation.databinding.ItemIndicatorBinding

class IndicatorAdapter(
    private val onIndicatorClickListener: ((Indicator) -> Unit)? = null
) : RecyclerView.Adapter<IndicatorAdapter.IndicatorVH>() {

    private val itemDiffCallback = object : DiffUtil.ItemCallback<Indicator>() {
        override fun areItemsTheSame(oldItem: Indicator, newItem: Indicator): Boolean {
            return oldItem.codigo == newItem.codigo
        }

        override fun areContentsTheSame(oldItem: Indicator, newItem: Indicator): Boolean {
            return oldItem == newItem
        }

    }
    private var mDiffer = AsyncListDiffer(this, itemDiffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndicatorVH {
        return IndicatorVH.create(parent)
    }

    override fun onBindViewHolder(holder: IndicatorVH, position: Int) {
        holder.bind(mDiffer.currentList[position], onIndicatorClickListener)
    }

    override fun getItemCount(): Int = mDiffer.currentList.size

    fun submitList(indicators: List<Indicator>) {
        mDiffer.submitList(indicators)
    }

    class IndicatorVH(
        private val biding: ItemIndicatorBinding,
    ) : RecyclerView.ViewHolder(biding.root) {


        fun bind(item: Indicator, onIndicatorClickListener: ((Indicator) -> Unit)?) {
            with(biding) {
                nameIndicator.text = item.nombre
                valueIndicator.text = item.valor.toString()
            }
            itemView.setOnClickListener {
                onIndicatorClickListener.let {
                    it?.invoke(item)
                }
            }

        }

        companion object {
            fun create(parent: ViewGroup): IndicatorVH {
                val biding =
                    ItemIndicatorBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return IndicatorVH(biding)
            }
        }

    }

}
