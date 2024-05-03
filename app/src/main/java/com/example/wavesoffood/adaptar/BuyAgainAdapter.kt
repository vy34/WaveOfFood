package com.example.wavesoffood.adaptar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wavesoffood.databinding.BuyAgainItemBinding

class BuyAgainAdapter(private val buyAgainFoodName: Array<String>,
                      private val buyAgainFoodPrice: Array<String>,
                      private val buyAgainFoodImage: Array<Int>
):RecyclerView.Adapter<BuyAgainAdapter.BuyAgainViewHolder>() {


    override fun onBindViewHolder(holder: BuyAgainViewHolder, position: Int) {
        holder.bind(buyAgainFoodName[position],
                    buyAgainFoodPrice[position],
                    buyAgainFoodImage[position])
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuyAgainViewHolder {
        val binding=BuyAgainItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BuyAgainViewHolder(binding) }

    override fun getItemCount(): Int =buyAgainFoodName.size
    class BuyAgainViewHolder(private val binding:BuyAgainItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(foodName: String, foodPrice: String, foodImage: Int) {
            binding.buyAgainFoodName.text=foodName
            binding.buyAgainFoodPrice.text=foodPrice
            binding.buyAgainFoodImage.setImageResource(foodImage)

        }
    }

}