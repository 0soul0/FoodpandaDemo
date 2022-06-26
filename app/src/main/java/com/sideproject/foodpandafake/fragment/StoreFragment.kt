package com.sideproject.foodpandafake.fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.sideproject.foodpandafake.MainViewModel
import com.sideproject.foodpandafake.R
import com.sideproject.foodpandafake.databinding.FragmentStoreBinding
import com.sideproject.foodpandafake.util.FragmentUtil
import com.sideproject.foodpandafake.util.NetworkUtil.checkForInternet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StoreFragment : Fragment(R.layout.fragment_store) {

    val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentStoreBinding
    private lateinit var storeAdapter: StoreAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        FragmentUtil.setBottomNavHideOrVisible(mainViewModel)
        binding = FragmentStoreBinding.bind(view)

        setAdapter()
        observerData()
    }

    private fun observerData() {
        if (checkForInternet(requireContext())) { //判斷是否有網路
            setProcessBarStateAndRecycleViewState(View.VISIBLE, View.GONE)
            mainViewModel.getStore(requireContext())?.observe(viewLifecycleOwner) {
                storeAdapter.setData(it)
                setProcessBarStateAndRecycleViewState(View.GONE, View.VISIBLE)
            }
        } else {
            Toast.makeText(requireContext(), "disconnect", Toast.LENGTH_SHORT).show()
        }

    }

    private fun setProcessBarStateAndRecycleViewState(
        processBarState: Int,
        recyclerViewState: Int
    ) {
        binding.progressBarStore.visibility = processBarState
        binding.rvStore.visibility=recyclerViewState
    }

    private fun setAdapter() {
        activity?.let {
            binding.rvStore.apply {
                storeAdapter = StoreAdapter()
                storeAdapter.setOnItemClickLister { store, _ ->
                    val bundle = bundleOf(
                        "store_name" to store.name,
                        "store_id" to store.id,
                        "store_position" to store.position
                    )
                    mainViewModel.visitStoreGPSPosition = store.gpsPosition
                    findNavController().navigate(R.id.storeMenuFragment, bundle)
                }
                adapter = storeAdapter
                layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.shapping_cart_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.shapping_cart -> findNavController().navigate(R.id.shoppingCartFragment)
        }
        return super.onOptionsItemSelected(item)
    }

}