package com.sideproject.foodpandafake.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sideproject.foodpandafake.MainViewModel
import com.sideproject.foodpandafake.R
import com.sideproject.foodpandafake.databinding.FragmentShoppingCartHistoryBinding
import com.sideproject.foodpandafake.model.StateConsumption
import com.sideproject.foodpandafake.util.FragmentUtil
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ShoppingCartHistoryFragment : Fragment(R.layout.fragment_shopping_cart_history){

    private lateinit var binding: FragmentShoppingCartHistoryBinding
    val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var shoppingCartAdapter: ShoppingCartAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        FragmentUtil.setBottomNavHideOrVisible(mainViewModel)
        binding = FragmentShoppingCartHistoryBinding.bind(view)

        setAdapter()
        observerShoppingCartHistory()
    }

    private fun observerShoppingCartHistory() {
        mainViewModel.getShoppingCartHistory(StateConsumption.BOUGHT).observe(viewLifecycleOwner) {
            shoppingCartAdapter.setData(it)
        }
    }


    private fun setAdapter() {
        activity?.let {
            binding.rvStore.apply {
                shoppingCartAdapter = ShoppingCartAdapter(context)
                adapter = shoppingCartAdapter
                layoutManager = LinearLayoutManager(it)
            }
        }
    }


}