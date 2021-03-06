package com.aptenobytes.bob.library.base.presentation.form.view

import android.content.Context
import com.aptenobytes.bob.library.base.databinding.EmptyBinding
import com.aptenobytes.bob.library.base.presentation.form.elements.BaseFormElement
import com.aptenobytes.bob.library.base.presentation.form.elements.EmptyElement

class EmptyViewHolder(context: Context, val binding: EmptyBinding) : BaseFormElementViewHolder(context, binding) {
    override fun bind(formElement: BaseFormElement<*>) {
        binding.viewModel = formElement as EmptyElement?
    }
}
