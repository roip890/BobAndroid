package com.aptenobytes.bob.library.base.presentation.navigation

import androidx.navigation.NavDirections

class NavManager {
    private var navDirectionEventListener: ((navDirections: NavDirections) -> Unit)? = null
    private var navResEventListener: ((resId: Int) -> Unit)? = null
    private var navUpEventListener: (() -> Unit)? = null
    private var setupControllerWithMainNavigationEventListener: (() -> Unit)? = null
    private var setupControllerWithBottomNavigationEventListener: (() -> Unit)? = null
    private var appBarVisibilityEventListener: ((visible: Boolean) -> Unit)? = null
    private var bottomNavVisibilityEventListener: ((visible: Boolean) -> Unit)? = null

    // main nav
    fun setSetupControllerWithMainNavigationEvent(setupControllerWithMainNavigationEventListener: () -> Unit) {
        this.setupControllerWithMainNavigationEventListener = setupControllerWithMainNavigationEventListener
    }

    fun navigateToMainNav() {
        setupControllerWithMainNavigationEventListener?.invoke()
    }

    // bottom nav
    fun setSetupControllerWithBottomNavigationEvent(setupControllerWithBottomNavigationEventListener: () -> Unit) {
        this.setupControllerWithBottomNavigationEventListener = setupControllerWithBottomNavigationEventListener
    }

    fun navigateToBottomNav() {
        setupControllerWithBottomNavigationEventListener?.invoke()
    }

    // navigate up
    fun setOnNavUpEvent(navUpEventListener: () -> Unit) {
        this.navUpEventListener = navUpEventListener
    }

    fun navigateUp() {
        navUpEventListener?.invoke()
    }

    // direction
    fun setOnDirectionNavEvent(navDirectionEventListener: (navDirections: NavDirections) -> Unit) {
        this.navDirectionEventListener = navDirectionEventListener
    }

    fun navigateDirection(navDirections: NavDirections) {
        navDirectionEventListener?.invoke(navDirections)
    }

    // res
    fun setOnResNavEvent(navResEventListener: (resId: Int) -> Unit) {
        this.navResEventListener = navResEventListener
    }

    fun navigateRes(resId: Int) {
        navResEventListener?.invoke(resId)
    }

    // app bar visibility
    fun setOnAppBarVisibilityEvent(appBarVisibilityEventListener: (visible: Boolean) -> Unit) {
        this.appBarVisibilityEventListener = appBarVisibilityEventListener
    }

    fun setAppBarVisible(visible: Boolean) {
        appBarVisibilityEventListener?.invoke(visible)
    }

    // bottom nav visibility
    fun setOnBottomNavVisibilityEvent(bottomNavVisibilityEventListener: (visible: Boolean) -> Unit) {
        this.bottomNavVisibilityEventListener = bottomNavVisibilityEventListener
    }

    fun setBottomNavVisible(visible: Boolean) {
        bottomNavVisibilityEventListener?.invoke(visible)
    }


}
