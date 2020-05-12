package com.aptenobytes.bob.library.base.presentation.form.view

import android.content.Context
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.aptenobytes.bob.library.base.presentation.form.elements.BaseFormElement

abstract class BaseFormElementViewHolder(
    val context: Context,
    binding: ViewDataBinding
) : RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(formElement: BaseFormElement<*>);
}
