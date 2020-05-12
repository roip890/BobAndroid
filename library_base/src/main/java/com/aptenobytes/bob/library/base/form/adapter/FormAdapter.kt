package com.aptenobytes.bob.library.base.form.adapter

import android.content.Context
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import com.aptenobytes.bob.library.base.R
import com.aptenobytes.bob.library.base.form.elements.*
import com.aptenobytes.bob.library.base.form.layouts.FormLayout
import com.aptenobytes.bob.library.base.form.view.*
import com.aptenobytes.bob.library.base.recyclerview.adapter.RecyclerViewAdapter
import com.aptenobytes.bob.library.base.recyclerview.adapter.adapter

fun formAdapter(
    context: Context,
    lifecycleOwner: LifecycleOwner
): RecyclerViewAdapter<BaseFormElement<*>?, BaseFormElementViewHolder> {
    return adapter(
        items = arrayListOf(),
        withGetItemCount = { adapter -> adapter.items.size },
        withGetItemViewType = { adapter, position ->
            when(adapter.items[position]) {
                is ButtonElement -> FormLayout.BUTTON.ordinal
                is DateTimeElement -> FormLayout.DATETIME.ordinal
                is MultiChoiceElement<*> -> FormLayout.MULTI_CHOICE.ordinal
                is SingleChoiceElement<*> -> FormLayout.SINGLE_CHOICE.ordinal
                is ListElement<*> -> FormLayout.LIST_CHOICE.ordinal
                is EditTextElement<*> -> FormLayout.EDIT_TEXT.ordinal
                is TextViewElement<*> -> FormLayout.TEXT_VIEW.ordinal
                else -> FormLayout.EMPTY.ordinal
            }
        },
        withOnCreateViewHolder = { _, parent, viewType ->
            val inflater = LayoutInflater.from(parent.context)
            when(viewType) {
                FormLayout.BUTTON.ordinal -> ButtonViewHolder(context, DataBindingUtil.inflate(inflater, R.layout.button, parent, false)).apply {
                    binding.lifecycleOwner = lifecycleOwner
                }
                FormLayout.SINGLE_CHOICE.ordinal -> SingleChoiceViewHolder(context, DataBindingUtil.inflate(inflater, R.layout.single_choice, parent, false)).apply {
                    binding.lifecycleOwner = lifecycleOwner
                }
                FormLayout.LIST_CHOICE.ordinal -> ListViewHolder(context, DataBindingUtil.inflate(inflater, R.layout.list, parent, false)).apply {
                    binding.lifecycleOwner = lifecycleOwner
                }
                FormLayout.MULTI_CHOICE.ordinal -> MultiChoiceViewHolder(context, DataBindingUtil.inflate(inflater, R.layout.multi_choice, parent, false)).apply {
                    binding.lifecycleOwner = lifecycleOwner
                }
                FormLayout.DATETIME.ordinal -> DateTimeViewHolder(context, DataBindingUtil.inflate(inflater, R.layout.date_time, parent, false)).apply {
                    binding.lifecycleOwner = lifecycleOwner
                }
                FormLayout.EDIT_TEXT.ordinal -> EditTextViewHolder(context, DataBindingUtil.inflate(inflater, R.layout.edit_text, parent, false)).apply {
                    binding.lifecycleOwner = lifecycleOwner
                }
                FormLayout.TEXT_VIEW.ordinal -> TextViewViewHolder(context, DataBindingUtil.inflate(inflater, R.layout.text_view, parent, false)).apply {
                    binding.lifecycleOwner = lifecycleOwner
                }
                else -> EmptyViewHolder(context, DataBindingUtil.inflate(inflater, R.layout.empty, parent, false)).apply {
                    binding.lifecycleOwner = lifecycleOwner
                }
            }
        },
        withOnBindViewHolder = { adapter, holder, position ->
            adapter.items[position]?.let { holder.bind(it) }
        }
    )
}
