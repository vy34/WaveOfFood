package com.example.wavesoffood.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wavesoffood.CongratsBottomSheet
import com.example.wavesoffood.PayOutActivity
import com.example.wavesoffood.R
import com.example.wavesoffood.adaptar.CartAdapter
import com.example.wavesoffood.databinding.FragmentCartBinding
import com.example.wavesoffood.model.CartItems
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class CartFragment : Fragment() {
    private lateinit var binding:FragmentCartBinding
    private lateinit var foodNames:MutableList<String>
    private lateinit var foodPrices:MutableList<String>
    private lateinit var foodImagesUri:MutableList<String>
    private lateinit var foodDescriptions: MutableList<String>
    private lateinit var foodIngredient:MutableList<String>
    private lateinit var Quatity:MutableList<Int>
    private lateinit var auth: FirebaseAuth
    private lateinit var database:FirebaseDatabase
    private lateinit var cartAdapter:CartAdapter
    private lateinit var userId:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)

       auth=FirebaseAuth.getInstance()
        reteriveCartItems()

        binding.proceedButton.setOnClickListener {
            //get order items details befor proceeding to check out
            getOrderItemsDetail()

        }

        return binding.root
    }

    private fun getOrderItemsDetail() {
        val orderIdReference:DatabaseReference=database.reference.child("user").child(userId).child("CartItems")
        val foodName= mutableListOf<String>()
        val foodPrice= mutableListOf<String>()
        val foodImage= mutableListOf<String>()
        val foodDescription= mutableListOf<String>()
        val foodIngredient= mutableListOf<String>()
        //get items quantity
        val foodQuantities=cartAdapter.getUpdatedItemsQuantities()
        orderIdReference.addListenerForSingleValueEvent(object :ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children){
                    //get the cartItem to respected list
                    val orderItems=foodSnapshot.getValue(CartItems::class.java)

                    //add cart items details to the list
                    orderItems?.foodName?.let { foodName.add(it) }
                    orderItems?.foodPrice?.let { foodPrice.add(it) }
                    orderItems?.foodImage?.let { foodImage.add(it) }
                    orderItems?.foodDescription?.let { foodDescription.add(it) }
                    orderItems?.foodIngredient?.let { foodIngredient.add(it) }

                }
                orderNow(foodName,foodPrice,foodDescription,foodIngredient,foodImage,foodQuantities)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Order making failed. Please try again", Toast.LENGTH_SHORT).show()
            }

        })

    }
    private fun orderNow(
        foodName: MutableList<String>,
        foodPrice: MutableList<String>,
        foodDescription: MutableList<String>,
        foodIngredient: MutableList<String>,
        foodImage: MutableList<String>,
        foodQuantities: MutableList<Int>
    ) {
        if (isAdded && context !=null){
            val intent=Intent(requireContext(),PayOutActivity::class.java)
            intent.putExtra("FoodItemName",foodName as ArrayList<String>)
            intent.putExtra("FoodItemPrice",foodPrice as ArrayList<String>)
            intent.putExtra("FoodItemImage",foodImage as ArrayList<String>)
            intent.putExtra("FoodItemDescription",foodDescription as ArrayList<String>)
            intent.putExtra("FoodItemIngredient",foodIngredient as ArrayList<String>)
            intent.putExtra("FoodItemQuantities",foodQuantities as ArrayList<Int>)
            startActivity(intent)
        }
}


    private fun reteriveCartItems() {
        database= FirebaseDatabase.getInstance()
        userId=auth.currentUser?.uid?:""
        val foodReference:DatabaseReference =database.reference.child("user").child(userId).child("CartItems")

        //list to store cartItem
        foodNames= mutableListOf()
        foodPrices= mutableListOf()
        foodImagesUri= mutableListOf()
        foodIngredient= mutableListOf()
        foodDescriptions= mutableListOf()
        Quatity= mutableListOf()


        //fetch data from database
        foodReference.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children){
                    //get the add item from the child node
                    var cartItems=foodSnapshot.getValue(CartItems::class.java)

                    //add cart items details to the list
                    cartItems?.foodName?.let { foodNames.add(it) }
                    cartItems?.foodPrice?.let { foodPrices.add(it) }
                    cartItems?.foodImage?.let { foodImagesUri.add(it) }
                    cartItems?.foodDescription?.let { foodDescriptions.add(it) }
                    cartItems?.foodIngredient?.let { foodIngredient.add(it) }
                    cartItems?.foodQuantity?.let { Quatity.add(it) }

                }
                setAdapter()
            }

            private fun setAdapter() {
                cartAdapter=CartAdapter(requireContext(),foodNames,foodPrices,foodImagesUri,foodDescriptions,foodIngredient,Quatity)
                binding.cartRecyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
                binding.cartRecyclerView.adapter = cartAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "data not fetch", Toast.LENGTH_SHORT).show()
            }

        })

    }

    companion object {

    }
}
