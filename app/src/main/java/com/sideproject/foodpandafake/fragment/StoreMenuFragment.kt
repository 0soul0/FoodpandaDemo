package com.sideproject.foodpandafake.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sideproject.foodpandafake.MainViewModel
import com.sideproject.foodpandafake.R
import com.sideproject.foodpandafake.databinding.FragmentStoreMenuBinding
import com.sideproject.foodpandafake.model.ShoppingCart
import com.sideproject.foodpandafake.util.FragmentUtil.setBottomNavHideOrVisible
import com.sideproject.foodpandafake.util.NetworkUtil
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.properties.Delegates

@AndroidEntryPoint
class StoreMenuFragment : Fragment(R.layout.fragment_store_menu) {

    val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentStoreMenuBinding
    private lateinit var storeMenuAdapter: StoreMenuAdapter

    private lateinit var storeId: UUID
    private lateinit var storeName: String
    private var storePosition by Delegates.notNull<Int>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setBottomNavHideOrVisible(mainViewModel, View.GONE)
        binding = FragmentStoreMenuBinding.bind(view)

        getData()
        setAdapter()
        observerData()
        setGotoShoppingCart()
    }

    private fun setGotoShoppingCart() {
        binding.btnGoToShoppingCart.setOnClickListener {
            val bundle = bundleOf(
                "store_id" to storeId
            )
            findNavController().navigate(R.id.shoppingCartFragment, bundle)
        }
    }

    private fun getData() {
        storeName = arguments?.getString("store_name")!!
        storeId = (arguments?.get("store_id") as UUID?)!!
        storePosition = arguments?.getInt("store_position")!!
    }

    private fun observerData() {
        if (NetworkUtil.checkForInternet(requireContext())) { //判斷是否有網路
            Log.v("TAG_CHECK", "connect $storeId")
            mainViewModel.getMenu(storePosition, storeId)?.observe(viewLifecycleOwner) {
                storeMenuAdapter.setData(it)
            }
        } else {
            Toast.makeText(requireContext(), "disconnect", Toast.LENGTH_SHORT).show()
        }

    }

    private fun setAdapter() {
        activity?.let {
            binding.rvStore.apply {
                storeMenuAdapter = StoreMenuAdapter()
                storeMenuAdapter.setOnItemClickLister { menu, _ ->

                    mainViewModel.addShoppingCart(
                        ShoppingCart(
                            menuItemId = menu.id,
                            menuItemName = menu.menuName,
                            storeName = storeName,
                            storeId = storeId,
                            menuNumber = mainViewModel.menuNumber!!,
                            menuPrice = menu.price
                        )
                    )
                    Toast.makeText(requireContext(), "加入1份 ${menu.menuName}", Toast.LENGTH_SHORT)
                        .show()
                }
                adapter = storeMenuAdapter
                layoutManager = LinearLayoutManager(it)
            }
        }
    }


}