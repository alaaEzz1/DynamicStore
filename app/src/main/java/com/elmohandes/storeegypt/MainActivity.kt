package com.elmohandes.storeegypt

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.elmohandes.storeegypt.databinding.ActivityMainBinding
import com.elmohandes.storeegypt.utils.CustomProgressDialog
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    //banner ad in coupon fragment == true
    //make listeners and product details fragment
    //connect with database and insert data
    //make ui for insert data
    //use room database to store data in cash memory
    //add data to favourite
    //make animation
    //make details in more fragment
    //main menu - about us / help /logout
    //fix back stack after pressing back == true

    private lateinit var home: LinearLayout
    private lateinit var deals: LinearLayout
    private lateinit var favourite: LinearLayout
    private lateinit var more: LinearLayout
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private lateinit var auth: FirebaseAuth
    private var navigate:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        home = findViewById(R.id.main_nav_home)
        deals = findViewById(R.id.main_nav_deals)
        favourite = findViewById(R.id.main_nav_favourite)
        more = findViewById(R.id.main_nav_more)

        auth =FirebaseAuth.getInstance()

        navHostFragment = supportFragmentManager.findFragmentById(R.id.main_nav_host) as
                NavHostFragment
        navController = navHostFragment.navController

        //hide recyclerview when login
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.loginFragment) {
                hideBottomActionBarAndSupportedActionBar()
            } else if (destination.id == R.id.signUpFragment) {
                hideBottomActionBarAndSupportedActionBar()
            } else {
                showBottomActionBarAndSupportedActionBar()
            }
        }

        setupActions()

        if (auth.currentUser != null){
            Log.d("current-user","${auth.currentUser!!.uid}")
        }else{
            Log.d("current-user","no account found")
        }
    }

    private fun showBottomActionBarAndSupportedActionBar() {
        val bottomAppBar = findViewById<BottomAppBar>(R.id.main_bottom_app_bar)
        val coordinator = findViewById<CoordinatorLayout>(R.id.main_coordinator)
        bottomAppBar.visibility = View.VISIBLE
        coordinator.visibility = View.VISIBLE
        supportActionBar?.show()
    }

    private fun hideBottomActionBarAndSupportedActionBar() {
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
            checkNavigate()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                Handler.createAsync(Looper.myLooper()!!).postDelayed({
                    navController.navigate(R.id.homeFragment)
                    dialog.dismissDialog()
                }, 1000)
            } else {
                navController.navigate(R.id.homeFragment)
                dialog.dismissDialog()
            }
        }
        deals.setOnClickListener {
            dialog.startLoading()
            checkNavigate()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                Handler.createAsync(Looper.myLooper()!!).postDelayed({
                    navController.navigate(R.id.dealsFragment)
                    dialog.dismissDialog()
                }, 1000)
            } else {
                navController.navigate(R.id.dealsFragment)
                dialog.dismissDialog()
            }
        }

        favourite.setOnClickListener {
            dialog.startLoading()
            checkNavigate()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                Handler.createAsync(Looper.myLooper()!!).postDelayed({
                    navController.navigate(R.id.favouriteFragment)
                    dialog.dismissDialog()
                }, 1000)
            } else {
                navController.navigate(R.id.favouriteFragment)
                dialog.dismissDialog()
            }
        }

        more.setOnClickListener {
            dialog.startLoading()
            checkNavigate()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                Handler.createAsync(Looper.myLooper()!!).postDelayed({
                    navController.navigate(R.id.moreFragment)
                    dialog.dismissDialog()
                }, 1000)
            } else {
                navController.navigate(R.id.moreFragment)
                dialog.dismissDialog()
            }
        }
    }

    private fun checkNavigate(){
        if (navigate == 0){
            Log.d("backStatus","on start")
            navigate++
        }else{
            navController.navigateUp()
            //Toast.makeText(applicationContext, "$navigate", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.home_menu_logout -> {
                val dialog = CustomProgressDialog(this)
                logoutFromDatabase(dialog)
                true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun logoutFromDatabase(dialog: CustomProgressDialog) {
        dialog.startLoading()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            Handler.createAsync(Looper.myLooper()!!).postDelayed({
                dialog.dismissDialog()
            }, 1000)
        }else{
            dialog.dismissDialog()
        }
        auth.signOut()
        navController.popBackStack(R.id.homeFragment, true)
        navController.navigate(R.id.loginFragment)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        navigate = 0
    }
}