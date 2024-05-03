package com.example.wavesoffood

import android.net.Uri
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.wavesoffood.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {
    private lateinit var  binding:ActivityDetailsBinding
    private var foodName:String?=null
    private  var foodPrice:String?=null
    private  var foodDescription:String?=null
    private  var foodImage:String?=null
    private  var foodIngredient:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        foodName=intent.getStringExtra("MenuItemName")
        foodPrice=intent.getStringExtra("MenuItemPrice")
        foodDescription=intent.getStringExtra("MenuItemDescription")
        foodImage=intent.getStringExtra("MenuItemImage")
        foodIngredient=intent.getStringExtra("MenuItemIngridients")

        with(binding){
            detailsFoodName.text=foodName
            detailDescription.text=foodDescription
            detailIngredients.text=foodIngredient
            Glide.with(this@DetailsActivity).load(Uri.parse(foodImage)).into(detailFoodImage)


        }


        binding.imageButton.setOnClickListener {
            finish()
        }

    }
}