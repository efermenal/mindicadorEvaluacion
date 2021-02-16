package com.example.mindicadorevaluation.features.detail.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mindicadorevaluation.core.models.Indicator
import com.example.mindicadorevaluation.core.models.IndicatorResponse
import com.example.mindicadorevaluation.databinding.ItemIndicatorBinding
import timber.log.Timber

class IndicatorAdapter : RecyclerView.Adapter<IndicatorAdapter.IndicatorVH>() {

    inner class IndicatorVH(val biding : ItemIndicatorBinding) : RecyclerView.ViewHolder(biding.root)

    private val itemDiffCallback = object :DiffUtil.ItemCallback<Indicator>(){
        override fun areItemsTheSame(oldItem: Indicator, newItem: Indicator): Boolean {
            return oldItem.codigo == newItem.codigo
        }

        override fun areContentsTheSame(oldItem: Indicator, newItem: Indicator): Boolean {
            return  oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, itemDiffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndicatorVH {
        val biding = ItemIndicatorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IndicatorVH(biding)
    }

    private var onIndicatorClickListener : ((Indicator) -> Unit)? = null
    fun setOnIndicatorClickListener(listener : (Indicator)-> Unit){
        onIndicatorClickListener = listener
    }

    override fun onBindViewHolder(holder: IndicatorVH, position: Int) {

        Timber.d("position  $position")
        with(holder){
            with(differ.currentList[position]){
                biding.nameIndicator.text = nombre
                biding.valueIndicator.text = valor.toString()
                holder.itemView.setOnClickListener {
                    onIndicatorClickListener?.let { it(this) }
                }
            }
        }
    }

    override fun getItemCount(): Int = differ.currentList.size


}