package com.aptenobytes.bob.library.base.form.view

import android.content.Context
import com.aptenobytes.bob.library.base.databinding.EditTextBinding
import com.aptenobytes.bob.library.base.form.elements.BaseFormElement
import com.aptenobytes.bob.library.base.form.elements.EditTextElement

class EditTextViewHolder(
    context: Context,
    val binding: EditTextBinding
) : BaseFormElementViewHolder(context, binding) {
    override fun bind(formElement: BaseFormElement<*>) {
        binding.viewModel = formElement as EditTextElement<*>?
        binding.executePendingBindings()
    }
}
