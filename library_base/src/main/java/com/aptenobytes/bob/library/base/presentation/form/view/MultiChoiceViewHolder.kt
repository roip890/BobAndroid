package com.aptenobytes.bob.library.base.presentation.form.view

import android.content.Context
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItemsMultiChoice
import com.aptenobytes.bob.library.base.databinding.MultiChoiceBinding
import com.aptenobytes.bob.library.base.presentation.form.elements.BaseFormElement
import com.aptenobytes.bob.library.base.presentation.form.elements.MultiChoiceElement

class MultiChoiceViewHolder(
    context: Context,
    val binding: MultiChoiceBinding
) : BaseFormElementViewHolder(context, binding) {

    override fun bind(formElement: BaseFormElement<*>) {
        binding.viewModel = formElement as MultiChoiceElement<*>?
        binding.executePendingBindings()
        binding.editText.setOnClickListener {
            MaterialDialog(context).show {
                listItemsMultiChoice(
                    items = binding.viewModel?.items?.map { binding.viewModel?.viewItemValue?.invoke(it).toString() },
                    initialSelection = (binding.viewModel?.value?.value as ArrayList<*>?)?.mapNotNull {
                        binding.viewModel?.items?.indexOf(it)}?.toIntArray() ?: intArrayOf()
                ) { _, indices, text ->
                    binding.viewModel?.value?.postValue(
                        binding.viewModel?.items?.filterNotNull()?.filterIndexed { index, _ -> index in indices })
                }
                positiveButton(text = "Select")
                title(text = binding.viewModel?.hint?.value as String?)
            }
        }
    }
}
