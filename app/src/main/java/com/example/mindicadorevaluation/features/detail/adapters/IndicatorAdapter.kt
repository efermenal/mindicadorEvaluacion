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

    var differ = AsyncListDiffer(this, itemDiffCallback)
    private var listCopy = mutableListOf<Indicator>()

    fun updateList(list : List<Indicator>){
        differ.submitList(list)
        listCopy.clear()
        listCopy.addAll(list)
    }



    fun filterByCode(code : String){
        listCopy.clear()
        if (code.isEmpty()){
            listCopy.addAll(differ.currentList.toList())
        }else{
            val newList = mutableListOf<Indicator>()
            for (item in differ.currentList){
                if (item.codigo.contains(code)){
                    newList.add(item)
                }
            }
            listCopy.addAll(newList)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndicatorVH {
        val biding = ItemIndicatorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IndicatorVH(biding)
    }

    private var onIndicatorClickListener : ((Indicator) -> Unit)? = null
    fun setOnIndicatorClickListener(listener : (Indicator)-> Unit){
        onIndicatorClickListener = listener
    }

    override fun onBindViewHolder(holder: IndicatorVH, position: Int) {

        with(holder){
           // with(differ.currentList[position]){
            with(listCopy[position]){
                biding.nameIndicator.text = nombre
                biding.valueIndicator.text = valor.toString()
                holder.itemView.setOnClickListener {
                    onIndicatorClickListener?.let { it(this) }
                }
            }
        }
    }

    override fun getItemCount(): Int = listCopy.size

}