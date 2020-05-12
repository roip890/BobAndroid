package com.aptenobytes.bob.library.base.presentation.form.view

import android.content.Context
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.datetime.dateTimePicker
import com.aptenobytes.bob.library.base.databinding.DateTimeBinding
import com.aptenobytes.bob.library.base.presentation.form.elements.BaseFormElement
import com.aptenobytes.bob.library.base.presentation.form.elements.DateTimeElement
import java.util.*

class DateTimeViewHolder(
    context: Context,
    val binding: DateTimeBinding
) : BaseFormElementViewHolder(context, binding) {

    override fun bind(formElement: BaseFormElement<*>) {
        binding.viewModel = formElement as DateTimeElement?
        binding.executePendingBindings()
        binding.editText.setOnClickListener {
            MaterialDialog(context).show {
                dateTimePicker(
                    requireFutureDateTime = true,
                    currentDateTime = Calendar.getInstance().apply {
                        time = binding.viewModel?.value?.value ?: Date()
                    }
                ) { _, dateTime ->
                    binding.viewModel?.value?.postValue(dateTime.time)
                }
            }
        }
    }
}
