package com.aptenobytes.bob.library.base.presentation.form.view

import android.content.Context
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import com.aptenobytes.bob.library.base.databinding.ListBinding
import com.aptenobytes.bob.library.base.presentation.form.elements.BaseFormElement
import com.aptenobytes.bob.library.base.presentation.form.elements.ListElement

class ListViewHolder(
    context: Context,
    val binding: ListBinding
) : BaseFormElementViewHolder(context, binding) {

    override fun bind(formElement: BaseFormElement<*>) {
        binding.viewModel = formElement as ListElement<*>?
        binding.executePendingBindings()
        binding.editText.setOnClickListener {
            MaterialDialog(context).show {
                listItems (
                    items = binding.viewModel?.items?.map { binding.viewModel?.viewItemValue?.invoke(it).toString() }
                ) { _, index, text ->
                    binding.viewModel?.value?.postValue(binding.viewModel?.items?.get(index))
                }
                positiveButton(text = "Select")
                title(text = binding.viewModel?.hint?.value as String?)
            }
        }
    }
}
