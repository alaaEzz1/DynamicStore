package com.elmohandes.storeegypt

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.LinearLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.elmohandes.storeegypt.databinding.ActivityMainBinding
import com.elmohandes.storeegypt.utils.CustomProgressDialog
import com.google.android.material.bottomappbar.BottomAppBar

class MainActivity : AppCompatActivity() {

    //home layout == true but need last finish
    //create fragments and connect it with bottom action bar
    //create user Authentication with firebase
    //create in deals fragment tap layout for products, coupons

    private lateinit var home: LinearLayout
    private lateinit var deals: LinearLayout
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        home = findViewById(R.id.main_nav_home)
        deals = findViewById(R.id.main_nav_deals)

        navHostFragment = supportFragmentManager.findFragmentById(R.id.main_nav_host) as
                NavHostFragment
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener{_ , destination, _ ->
            if (destination.id == R.id.loginFragment){
                hideBottomActionBarAndSupportedActionBar()
            }else if (destination.id == R.id.signUpFragment){
                hideBottomActionBarAndSupportedActionBar()
            }else{
                showBottomActionBarAndSupportedActionBar()
            }
        }

        setupActions()

    }

    private fun showBottomActionBarAndSupportedActionBar(){
        val bottomAppBar = findViewById<BottomAppBar>(R.id.main_bottom_app_bar)
        val coordinator = findViewById<CoordinatorLayout>(R.id.main_coordinator)
        bottomAppBar.visibility = View.VISIBLE
        coordinator.visibility = View.VISIBLE
        supportActionBar?.show()
    }
    private fun hideBottomActionBarAndSupportedActionBar(){
        val bottomAppBar = findViewById<BottomAppBar>(R.id.main_bottom_app_bar)
        val coordinator = findViewById<CoordinatorLayout>(R.id.main_coordinator)
        bottomAppBar.visibility = View.GONE
        coordinator.visibility = View.GONE
        supportActionBar?.hide()
    }

    private fun setupActions() {
        val dialog = CustomProgressDialog(this)
        home.setOnClickListener {
            dialog.startLoading()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                Handler.createAsync(Looper.myLooper()!!).postDelayed({
                    dialog.dismissDialog()
                    navController.navigateUp()
                    navController.navigate(R.id.homeFragment)
                }, 1000)
            } else {
                navController.navigateUp()
                navController.navigate(R.id.homeFragment)
                dialog.dismissDialog()
            }
        }
        deals.setOnClickListener {
            dialog.startLoading()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                Handler.createAsync(Looper.myLooper()!!).postDelayed({
                    navController.navigateUp()
                    navController.navigate(R.id.dealsFragment)
                    dialog.dismissDialog()
                }, 1000)
            } else {
                navController.navigateUp()
                navController.navigate(R.id.dealsFragment)
                dialog.dismissDialog()
            }
        }
    }
}