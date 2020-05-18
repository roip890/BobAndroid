package com.aptenobytes.bob.library.base.presentation.form.view

import android.content.Context
import com.aptenobytes.bob.library.base.databinding.PhoneBinding
import com.aptenobytes.bob.library.base.presentation.form.elements.BaseFormElement
import com.aptenobytes.bob.library.base.presentation.form.elements.PhoneElement
import io.michaelrocks.libphonenumber.android.NumberParseException
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil

class PhoneViewHolder(
    context: Context,
    val binding: PhoneBinding
) : BaseFormElementViewHolder(context, binding) {

    override fun bind(formElement: BaseFormElement<*>) {
        binding.viewModel = formElement as PhoneElement?
        binding.executePendingBindings()
        binding.countryCodePicker.registerCarrierNumberEditText(
            binding.editText
        )
        binding.lifecycleOwner?.let { lifecycleOwner ->
            binding.viewModel?.text?.observe(lifecycleOwner, androidx.lifecycle.Observer {
                if (binding.countryCodePicker.isValidFullNumber
                    && binding.countryCodePicker.fullNumber != binding.viewModel?.value?.value) {
                    binding.viewModel?.value?.postValue(binding.countryCodePicker.fullNumber)
                }
            })
            binding.viewModel?.value?.observe(lifecycleOwner, androidx.lifecycle.Observer {value ->
                if (binding.countryCodePicker.fullNumber != binding.viewModel?.value?.value) {
                    binding.countryCodePicker.fullNumber = value
                }
            })
            binding.viewModel?.initialValue?.observe(lifecycleOwner, androidx.lifecycle.Observer {initialValue ->
                if (binding.countryCodePicker.fullNumber != binding.viewModel?.value?.value) {
                    binding.countryCodePicker.fullNumber = initialValue
                }
            })
            binding.countryCodePicker.setOnCountryChangeListener {
                if (binding.countryCodePicker.isValidFullNumber
                    && binding.countryCodePicker.fullNumber != binding.viewModel?.value?.value) {
                    binding.viewModel?.value?.postValue(binding.countryCodePicker.fullNumber)
                }
            }
            binding.viewModel?.validate = {
                if (binding.countryCodePicker.isValidFullNumber) listOf("Please enter valid phone") else null
            }
        }
    }

}
