package com.sideproject.foodpandafake

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.sideproject.foodpandafake.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment

    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        bindNav()
        setupBottomNavMenu()
        observeBottomNavState()
        setToolbarTitle()
    }

    private fun bindNav() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_fragment) as NavHostFragment?
                ?: return
        navController = navHostFragment.navController
//        appBarConfiguration = AppBarConfiguration(navController.graph)
        appBarConfiguration = AppBarConfiguration(
            //不要返回的fragment
            setOf(
                R.id.storeFragment,
                R.id.shoppingCartHistoryFragment,
                R.id.reportAProblemFragment,
            )
        )
    }

    private fun setupBottomNavMenu() {
//        <!--   id 要對應 nav fragment id -->
        binding.bottomNavView.setupWithNavController(navController)
    }

    private fun observeBottomNavState() {
        mainViewModel.bottomNavState.observe(this) {
            binding.bottomNavView.visibility = it
        }
    }


    private fun setToolbarTitle() {
        setSupportActionBar(findViewById(R.id.toolbar_title))
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    //stack go back
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}