package com.aptenobytes.bob.library.base.presentation.form.view

import android.content.Context
import com.aptenobytes.bob.library.base.databinding.TextViewBinding
import com.aptenobytes.bob.library.base.presentation.form.elements.BaseFormElement
import com.aptenobytes.bob.library.base.presentation.form.elements.TextViewElement

    class TextViewViewHolder(
        context: Context,
        val binding: TextViewBinding
    ) : BaseFormElementViewHolder(context, binding) {
    override fun bind(formElement: BaseFormElement<*>) {
        binding.viewModel = formElement as TextViewElement<*>?
    }

}
