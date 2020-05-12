package com.aptenobytes.bob.library.base.presentation.form.view

import android.content.Context
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.aptenobytes.bob.library.base.databinding.SingleChoiceBinding
import com.aptenobytes.bob.library.base.presentation.form.elements.BaseFormElement
import com.aptenobytes.bob.library.base.presentation.form.elements.SingleChoiceElement

class SingleChoiceViewHolder(
    context: Context,
    val binding: SingleChoiceBinding
) : BaseFormElementViewHolder(context, binding) {

    override fun bind(formElement: BaseFormElement<*>) {
        binding.viewModel = formElement as SingleChoiceElement<*>?
        binding.executePendingBindings()
        binding.editText.setOnClickListener {
            MaterialDialog(context).show {
                listItemsSingleChoice (
                    items = binding.viewModel?.items?.map { binding.viewModel?.viewItemValue?.invoke(it).toString() },
                    initialSelection = binding.viewModel?.items?.indexOf(binding.viewModel?.value?.value) ?: -1
                ) { _, index, text ->
                    binding.viewModel?.value?.postValue(binding.viewModel?.items?.get(index))
                }
                positiveButton(text = "Select")
                title(text = binding.viewModel?.hint?.value as String?)
            }
        }
    }
}
