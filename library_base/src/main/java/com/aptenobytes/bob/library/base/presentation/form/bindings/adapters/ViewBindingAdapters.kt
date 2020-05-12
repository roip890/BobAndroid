package com.aptenobytes.bob.library.base.presentation.form.bindings.adapters

import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import com.aptenobytes.bob.library.base.presentation.form.bindings.classes.HorizontalGravity
import com.aptenobytes.bob.library.base.presentation.form.bindings.classes.Margin
import com.aptenobytes.bob.library.base.presentation.form.bindings.classes.Padding
import com.aptenobytes.bob.library.base.presentation.form.bindings.classes.VerticalGravity

// view margin
@BindingAdapter("layout_margin")
fun setLayoutMargin(view: View, margin: Margin?): Unit {
    margin?.let {
        val layoutParams = view.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.setMargins(margin.left, margin.top, margin.right, margin.bottom)
        layoutParams.marginStart = margin.start
        layoutParams.marginEnd = margin.end
        view.layoutParams = layoutParams
    }
    var t: TextInputLayout
}

// view padding
@BindingAdapter("layout_padding")
fun setLayoutPadding(view: View, padding: Padding?): Unit {
    padding?.let {
        view.setPadding(padding.left, padding.top, padding.right, padding.bottom)
        view.setPaddingRelative(padding.start, padding.top, padding.end, padding.bottom)
    }
}

// view width
@BindingAdapter("layout_width")
fun setLayoutWidth(view: View, width: Int?): Unit {
    width?.let {
        val layoutParams = view.layoutParams
        layoutParams.width = width
        view.layoutParams = layoutParams
    }
}

// view height
@BindingAdapter("layout_height")
fun setLayoutHeight(view: View, height: Int?) {
    height?.let {
        val layoutParams = view.layoutParams
        layoutParams.height = height
        view.layoutParams = layoutParams
    }
}

// horizontal gravity
@BindingAdapter("layout_horizontal_gravity")
fun setLayoutHorizontalGravity(view: View, horizontalGravity: HorizontalGravity?): Unit {
    horizontalGravity?.let {
        val parent = view.parent as? ConstraintLayout
        parent?.let{
            val set = ConstraintSet()
            set.clone(parent)
            set.clear(view.id, ConstraintSet.START)
            set.clear(view.id, ConstraintSet.END)
            set.clear(view.id, ConstraintSet.LEFT)
            set.clear(view.id, ConstraintSet.RIGHT)

            if (horizontalGravity.start)
                set.connect(view.id, ConstraintSet.START, parent.id, ConstraintSet.START,
                    (view.layoutParams as? ViewGroup.MarginLayoutParams)?.marginStart ?: 0);
            if (horizontalGravity.end)
                set.connect(view.id, ConstraintSet.END, parent.id, ConstraintSet.END,
                    (view.layoutParams as? ViewGroup.MarginLayoutParams)?.marginEnd ?: 0);
            if (horizontalGravity.left)
                set.connect(view.id, ConstraintSet.LEFT, parent.id, ConstraintSet.LEFT,
                    (view.layoutParams as? ViewGroup.MarginLayoutParams)?.leftMargin ?: 0);
            if (horizontalGravity.right)
                set.connect(view.id, ConstraintSet.RIGHT, parent.id, ConstraintSet.RIGHT,
                    (view.layoutParams as? ViewGroup.MarginLayoutParams)?.rightMargin ?: 0);
            if (horizontalGravity.center) {
                set.connect(view.id, ConstraintSet.START, parent.id, ConstraintSet.START,
                    (view.layoutParams as? ViewGroup.MarginLayoutParams)?.marginStart ?: 0);
                set.connect(view.id, ConstraintSet.END, parent.id, ConstraintSet.END,
                    (view.layoutParams as? ViewGroup.MarginLayoutParams)?.marginEnd ?: 0)
            }

            set.applyTo(parent)
        }
    }
}

// vertical gravity
@BindingAdapter("layout_vertical_gravity")
fun setLayoutVerticalGravity(view: View, verticalGravity: VerticalGravity?): Unit {
    verticalGravity?.let {
        val parent = view.parent as? ConstraintLayout
        parent?.let{
            val set = ConstraintSet()
            set.clone(parent)
            set.clear(view.id, ConstraintSet.TOP)
            set.clear(view.id, ConstraintSet.BOTTOM)

            if (verticalGravity.top)
                set.connect(view.id, ConstraintSet.TOP, parent.id, ConstraintSet.TOP,
                    (view.layoutParams as? ViewGroup.MarginLayoutParams)?.topMargin ?: 0);
            if (verticalGravity.bottom)
                set.connect(view.id, ConstraintSet.BOTTOM, parent.id, ConstraintSet.BOTTOM,
                    (view.layoutParams as? ViewGroup.MarginLayoutParams)?.bottomMargin ?: 0);
            if (verticalGravity.center) {
                set.connect(view.id, ConstraintSet.TOP, parent.id, ConstraintSet.TOP,
                    (view.layoutParams as? ViewGroup.MarginLayoutParams)?.topMargin ?: 0);
                set.connect(view.id, ConstraintSet.BOTTOM, parent.id, ConstraintSet.BOTTOM,
                    (view.layoutParams as? ViewGroup.MarginLayoutParams)?.bottomMargin ?: 0);
            }

            set.applyTo(parent)
        }
    }
}

// view background
@BindingAdapter("layout_background")
fun setLayoutBackground(view: View, background: Drawable?) {
    background?.let {
        view.background = background
    }
}

// view background
@BindingAdapter("layout_background_color")
fun setLayoutBackgroundColor(view: View, backgroundColor: Int?) {
    backgroundColor?.let {
        view.setBackgroundColor(backgroundColor)
    }
}
