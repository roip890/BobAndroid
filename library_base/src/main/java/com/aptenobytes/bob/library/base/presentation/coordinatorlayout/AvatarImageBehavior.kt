package com.aptenobytes.bob.library.base.presentation.coordinatorlayout

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.aptenobytes.bob.library.base.R


class AvatarImageBehavior @SuppressLint("PrivateResource") constructor(
    context: Context,
    attrs: AttributeSet?
) :
    CoordinatorLayout.Behavior<ImageView?>() {
    private var mStartXPosition = 0f
    private var mStartToolbarPosition = 0f
    private var mStartYPosition = 0f
    private var mFinalYPosition = 0f
    private val mFinalHeight: Float = context.resources.getDimensionPixelOffset(R.dimen.image_final_width).toFloat()
    private var mStartHeight = 0f
    private val mFinalXPosition: Float
    private val mStatusBarHeight: Float

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: ImageView,
        dependency: View
    ): Boolean {
        return dependency is Toolbar
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: ImageView,
        dependency: View
    ): Boolean {
        initProperties(child, dependency)
        val maxScrollDistance = (mStartToolbarPosition - mStatusBarHeight).toInt()
        val expandedPercentageFactor = dependency.y / maxScrollDistance
        val distanceYToSubtract: Float = ((mStartYPosition - mFinalYPosition)
                * (1f - expandedPercentageFactor)) + child.height / 2
        val distanceXToSubtract: Float = ((mStartXPosition - mFinalXPosition)
                * (1f - expandedPercentageFactor)) + child.width / 2
        val heightToSubtract = (mStartHeight - mFinalHeight) * (1f - expandedPercentageFactor)
        child.y = mStartYPosition - distanceYToSubtract
        child.x = mStartXPosition - distanceXToSubtract
        val lp: CoordinatorLayout.LayoutParams = child.layoutParams as CoordinatorLayout.LayoutParams
        lp.width = (mStartHeight - heightToSubtract).toInt()
        lp.height = (mStartHeight - heightToSubtract).toInt()
        child.layoutParams = lp
        return true
    }

    private fun initProperties(child: ImageView, dependency: View) {
        if (mStartYPosition == 0f) mStartYPosition = dependency.y.toFloat()
        if (mFinalYPosition == 0f) mFinalYPosition = (dependency.height / 2).toFloat()
        if (mStartHeight == 0f) mStartHeight = child.height.toFloat()
        if (mStartXPosition == 0f) mStartXPosition = (child.x + child.width / 2)
        if (mStartToolbarPosition == 0f) mStartToolbarPosition = dependency.y + dependency.height / 2
    }

    fun getStatusBarHeight(context: Context): Float {
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (resourceId != 0) {
            context.resources
                .getDimensionPixelSize(resourceId).toFloat()
        } else {
            50.toFloat()
        }
    }

    init {
        mFinalXPosition = (context.resources.getDimensionPixelOffset(R.dimen.abc_action_bar_content_inset_material)
                + mFinalHeight / 2)
        mStatusBarHeight = getStatusBarHeight(context)
    }
}
