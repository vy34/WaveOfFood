package com.example.wavesoffood.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wavesoffood.R
import com.example.wavesoffood.adaptar.MenuAdapter
import com.example.wavesoffood.databinding.FragmentSearchBinding
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.*


class SearchFragment : Fragment() {
    private lateinit var binding:FragmentSearchBinding
    private lateinit var adapter: MenuAdapter
    private val orignalMenuFoodName=  listOf("burger","sandwich", "mono", "item","burger","sandwich", "mono")
    private val orignalMenuItemPrice = listOf("$5","$10","$16","$8","$5","$10","$16")
    private val orignalMenuImage = listOf(
        R.drawable.menu1,
        R.drawable.menu2,
        R.drawable.menu3,
        R.drawable.menu4,
        R.drawable.menu5,
        R.drawable.menu6,
        R.drawable.menu7,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private val filteredMenuFoodName= mutableListOf<String>()
    private val filteredMenuItemPrice=mutableListOf<String>()
    private val filteredMenuImage= mutableListOf<Int>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentSearchBinding.inflate(inflater,container,false)
        adapter=MenuAdapter(filteredMenuFoodName,filteredMenuItemPrice,filteredMenuImage,requireContext())
        binding.menuRecyclerView.layoutManager=LinearLayoutManager(requireContext())
        binding.menuRecyclerView.adapter=adapter

        setupSearchView()

        showAllMenu()

        return binding.root
    }

    private fun showAllMenu() {
        filteredMenuFoodName.clear()
        filteredMenuItemPrice.clear()
        filteredMenuImage.clear()

        filteredMenuFoodName.addAll(orignalMenuFoodName)
        filteredMenuItemPrice.addAll(orignalMenuItemPrice)
        filteredMenuImage.addAll(orignalMenuImage)

        adapter.notifyDataSetChanged()

    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(/* listener = */ object : OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                filterMenuItem(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filterMenuItem(newText)
                return true
            }
        })
    }

    private fun filterMenuItem(query: String) {
        filteredMenuFoodName.clear()
        filteredMenuItemPrice.clear()
        filteredMenuImage.clear()


        orignalMenuFoodName.forEachIndexed { index, foodName ->
            if (foodName.contains(query,ignoreCase = true)){
                filteredMenuFoodName.add(foodName)
                filteredMenuItemPrice.add(orignalMenuItemPrice[index])
                filteredMenuImage.add(orignalMenuImage[index])

            }
        }
        adapter.notifyDataSetChanged()

    }

    companion object {

    }
}