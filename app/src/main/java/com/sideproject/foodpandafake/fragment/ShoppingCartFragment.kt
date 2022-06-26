package com.sideproject.foodpandafake.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.sideproject.foodpandafake.MainViewModel
import com.sideproject.foodpandafake.R
import com.sideproject.foodpandafake.databinding.FragmentShoppingCartBinding
import com.sideproject.foodpandafake.model.StateConsumption
import com.sideproject.foodpandafake.util.FragmentUtil
import com.sideproject.foodpandafake.util.PermissionUtil
import dagger.hilt.android.AndroidEntryPoint

@RequiresApi(Build.VERSION_CODES.N)
@AndroidEntryPoint
class ShoppingCartFragment : Fragment(R.layout.fragment_shopping_cart) {

    val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var shoppingCartAdapter: ShoppingCartAdapter
    private lateinit var binding: FragmentShoppingCartBinding
    private lateinit var googleMap: GoogleMap

    private var menuAmount = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        FragmentUtil.setBottomNavHideOrVisible(mainViewModel, View.GONE)
        binding = FragmentShoppingCartBinding.bind(view)

        checkAndSetGPSIsOpen()
        observerShoppingCart()
        setAdapter()
        setPostOrderOnclickLister()

        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
        setGoogleMap()

    }

    private fun setPostOrderOnclickLister() {
        binding.btnBuy.setOnClickListener {
            if (menuAmount == 0) {
                Toast.makeText(requireContext(), "無訂單", Toast.LENGTH_LONG)
                    .show()
                return@setOnClickListener
            }
            mainViewModel.updateShoppingCartStateByMenuNumber(StateConsumption.BOUGHT)
            Toast.makeText(requireContext(), "訂單送出", Toast.LENGTH_LONG)
                .show()
            findNavController().navigate(R.id.storeFragment, null)
        }
    }

    private fun observerShoppingCart() {
        mainViewModel.getShoppingCart(StateConsumption.BUYING).observe(viewLifecycleOwner) {
            menuAmount = it.size
            shoppingCartAdapter.setData(it)
        }
    }

    private fun setAdapter() {
        activity?.let {
            binding.rvStore.apply {
                binding.tvSumPrice.text =
                    String.format(context.getString(R.string.sum), 0)
                shoppingCartAdapter = ShoppingCartAdapter(context)
                shoppingCartAdapter.callbackData {
                    binding.tvSumPrice.text =
                        String.format(context.getString(R.string.sum), it)
                }
                adapter = shoppingCartAdapter
                layoutManager = LinearLayoutManager(it)
            }
        }
    }


    @SuppressLint("MissingPermission")
    private fun setGoogleMap() {
        if (!PermissionUtil().getLocalAndSetupPermission(requireActivity())) {
            return
        }
        if (mainViewModel.visitStoreGPSPosition.isEmpty()) {
            return
        }
        val supportMapFragment =
            childFragmentManager.findFragmentById(R.id.fragment_google_map) as SupportMapFragment
        supportMapFragment.getMapAsync { google ->
            googleMap = google
            google.isMyLocationEnabled = true
            // zoom level of google map (0 to 21)
            //1: world
            //5:LandMass
            //10:city
            //15:street
            //20:buildings
            val storeLocation = LatLng(
                mainViewModel.visitStoreGPSPosition[0],
                mainViewModel.visitStoreGPSPosition[1]
            )
            googleMap.addMarker(MarkerOptions().position(storeLocation).title("Store Location"))
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(storeLocation, 12f))
        }
    }


    private fun checkAndSetGPSIsOpen() {
        if (PermissionUtil().isOpenGPS(requireActivity())) {
            return
        }
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setTitle("請開啟GPS連結")
            .setMessage("為了提高定位的精準度，更好的為您服務，請開啟GPS")
            .setPositiveButton(
                "設置"
            ) { _, _ -> //跳轉到手機打開GPS頁面
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                gpsLauncher.launch(intent)
            }.show()
    }

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                setGoogleMap()
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                setGoogleMap()
            }
            else -> {
                PermissionUtil().getLocalAndSetupPermission(requireActivity())
            }
        }
    }

    private var gpsLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            setGoogleMap()
        }
}