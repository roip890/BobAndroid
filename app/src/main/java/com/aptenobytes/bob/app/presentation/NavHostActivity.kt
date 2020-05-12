package com.aptenobytes.bob.app.presentation

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.plusAssign
import androidx.navigation.ui.setupWithNavController
import com.aptenobytes.bob.R
import com.aptenobytes.bob.library.base.navigation.KeepStateNavigator
import com.aptenobytes.bob.library.base.presentation.activity.BaseActivity
import com.aptenobytes.bob.library.base.presentation.navigation.NavManager
import kotlinx.android.synthetic.main.activity_nav_host.*
import org.kodein.di.generic.instance

class NavHostActivity : BaseActivity() {

    override val layoutResId = R.layout.activity_nav_host

    private val navController get() = navHostFragment.findNavController()

    private val navManager: NavManager by instance<NavManager>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        // get fragment
//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment)!!
//
//        // setup custom navigator
//        val navigator = KeepStateNavigator(this, navHostFragment.childFragmentManager, R.id.navHostFragment)
//        navController.navigatorProvider += navigator
//

        bottomNav.setupWithNavController(navController)

        navManager.setOnNavEvent {
            navController.navigate(it)
        }

    }

//    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
//        super.onRestoreInstanceState(savedInstanceState)
//        setupBottomNavigationBar()
//    }
//
//    private fun initBottomNavigation() {
//        bottomNav.
//    }
//
//    private fun initNavManager() {
//        navManager.setOnNavEvent {
//            navController.navigate(it)
//        }
//    }
//
//
//
//    private fun setupBottomNavigationBar() {
//        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNav)
//
//        navController.
//        val navGraphIds = listOf(
//            R.navigation.feature_wish_nav_graph,
//            R.navigation.feature_profile_nav_graph,
//            R.navigation.feature_favourite_nav_graph
//        )
//
//        // Setup the bottom navigation view with a list of navigation graphs
//        val controller = bottomNavigationView.setupWithNavController(
//            navGraphIds = navGraphIds,
//            fragmentManager = supportFragmentManager,
//            containerId = R.id.navHostFragment,
//            intent = intent
//        )
//        currentNavController = controller
//    }
//
//    override fun onSupportNavigateUp(): Boolean {
//        return currentNavController?.value?.navigateUp() ?: false
//    }

}
