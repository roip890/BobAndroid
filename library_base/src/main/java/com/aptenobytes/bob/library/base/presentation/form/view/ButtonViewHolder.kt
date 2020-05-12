package com.aptenobytes.bob.library.base.presentation.form.view

import android.content.Context
import com.aptenobytes.bob.library.base.databinding.ButtonBinding
import com.aptenobytes.bob.library.base.presentation.form.elements.BaseFormElement
import com.aptenobytes.bob.library.base.presentation.form.elements.ButtonElement

class ButtonViewHolder(
        context: Context,
        val binding: ButtonBinding
    ) : BaseFormElementViewHolder(context, binding) {
    override fun bind(formElement: BaseFormElement<*>) {
        binding.viewModel = formElement as ButtonElement?
        binding.executePendingBindings()
    }

}
