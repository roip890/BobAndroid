package com.aptenobytes.bob.library.base.presentation.form.builder

import android.content.Context
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aptenobytes.bob.library.base.extensions.collections.toArrayList
import com.aptenobytes.bob.library.base.presentation.form.elements.BaseFormElement
import com.aptenobytes.bob.library.base.presentation.form.view.BaseFormElementViewHolder
import com.aptenobytes.bob.library.base.presentation.recyclerview.adapter.RecyclerViewAdapter

@FormDsl
class Form
@JvmOverloads constructor(
    context: Context,
    var recyclerView: RecyclerView? = null,
    val adapter: RecyclerViewAdapter<BaseFormElement<*>?, BaseFormElementViewHolder>
) {

    init {
        if (recyclerView != null) {
            attachRecyclerView(context, recyclerView)
        }
    }

    private fun attachRecyclerView(context: Context, recyclerView: RecyclerView?) {
        recyclerView?.let {
            // Set up the RecyclerView with the adapter
            it.layoutManager = LinearLayoutManager(context).apply {
                orientation = RecyclerView.VERTICAL
                stackFromEnd = false
            }
            it.adapter = adapter
            it.itemAnimator = DefaultItemAnimator()
            this.recyclerView = it
        }
    }

    fun <T : BaseFormElement<*>> addFormElement(formElement: T): T {
        this.adapter.items.add(formElement)
        this.adapter.notifyDataSetChanged()
        return formElement
    }

    fun addFormElements(formElements: List<BaseFormElement<*>>) {
        this.adapter.items.addAll(formElements)
        this.adapter.notifyDataSetChanged()
    }

    fun setFormElements(formElements: List<BaseFormElement<*>>) {
        this.adapter.items = formElements.toArrayList()
        this.adapter.notifyDataSetChanged()
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : BaseFormElement<*>> getFormElement(tag: Int): T {
        return this.adapter.items.first { it?.tag == tag } as T
    }

    fun getElementAtIndex(index: Int): BaseFormElement<*>? {
        return this.adapter.items[index]

    }

    fun clearAll() {
        this.adapter.items.forEach { it?.clear() }
    }

    val valid: Boolean
        get() = this.adapter.items.all { it?.valid?.value ?: false }

    val value: Map<String?, *>
        get() = this.adapter.items.associate { it?.key?.value to it?.value?.value }

}
