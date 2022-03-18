package com.huawei.qrdemo.ui.options

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.huawei.qrdemo.databinding.ItemScanOptionBinding
import com.huawei.qrdemo.model.Option

class OptionAdapter(private val options: List<Option>, private val itemClick: (Option) -> Unit) :
    RecyclerView.Adapter<OptionAdapter.OptionViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionViewHolder {
        return OptionViewHolder(
            ItemScanOptionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: OptionViewHolder, position: Int) {
        holder.bind(options[position])
        holder.binding.root.setOnClickListener {
            itemClick(options[position])
        }
    }

    override fun getItemCount(): Int = options.size

    class OptionViewHolder(val binding: ItemScanOptionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(option: Option) {
            binding.scanOption.text = option.name
        }
    }
}