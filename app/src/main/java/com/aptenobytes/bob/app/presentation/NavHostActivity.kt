package com.aptenobytes.bob.app.presentation

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.WindowManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.aptenobytes.bob.R
import com.aptenobytes.bob.library.base.presentation.activity.BaseActivity
import com.aptenobytes.bob.library.base.presentation.navigation.NavManager
import kotlinx.android.synthetic.main.activity_nav_host.*
import org.kodein.di.generic.instance

class NavHostActivity : BaseActivity() {

    override val layoutResId = R.layout.activity_nav_host

    private val navController get() = navHostFragment.findNavController()

//    private var currentNavController: LiveData<NavController>? = null

    private val navManager: NavManager by instance<NavManager>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        navManager.setOnDirectionNavEvent {
            navController.navigate(it)
        }

        navManager.setOnResNavEvent {
            navController.navigate(it)
        }

        navManager.setOnAppBarVisibilityEvent {visible ->
            appBarLayout.visibility = if (visible) VISIBLE else GONE
        }

        navManager.setOnBottomNavVisibilityEvent { visible ->
            bottomNav.visibility = if (visible) VISIBLE else GONE
        }

        navManager.setOnNavUpEvent {
            navController.navigateUp()
        }

        navManager.setSetupControllerWithMainNavigationEvent {
            setupControllerWishMainNavigationGraph()
        }

        navManager.setSetupControllerWithBottomNavigationEvent {
            setupControllerWishBottomNavigationGraph()
        }

        setupControllerWishMainNavigationGraph()

    }

    private fun setupControllerWishMainNavigationGraph() {
        navHostFragment?.findNavController()?.navInflater?.inflate(R.navigation.app_nav_graph)?.let { navGraph ->
            bottomNav.setupWithNavController(navController)
            navHostFragment.findNavController().graph = navGraph
            navManager.setAppBarVisible(visible = false)
            navManager.setBottomNavVisible(visible = false)
        }
    }

    private fun setupControllerWishBottomNavigationGraph() {

        navHostFragment?.findNavController()?.navInflater?.inflate(R.navigation.bottom_nav_graph)?.let { navGraph ->
            navHostFragment.findNavController().graph = navGraph
            navManager.setAppBarVisible(visible = true)
            navManager.setBottomNavVisible(visible = true)
        }

// try to make the nav bar persistence
//        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNav)
//
//        val navGraphIds = listOf(
//            R.id.featureWishNavGraph,
//            R.id.featureProfileNavGraph,
//            R.id.featureNotificationNavGraph
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
//        navManager.setAppBarVisible(visible = true)
//        navManager.setBottomNavVisible(visible = true)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() ?: false
    }

}
