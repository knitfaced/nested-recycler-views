package com.polly.nestedrecyclerviews

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

class CompoundAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    enum class ViewType {
        EMBEDDED, MONSTER, COMPOUND_HORDE_TITLE_ROW, COMPOUND_HORDE_NORMAL_ROW;

        /*
        Use this field in getItemViewType() implementations for child adapters - add an enum value and reference its value.
        Offsetting ordinal value by 100 here to make it more clear if child adapters fail to provide a view type int from the enum above.
        So if a child adapter returns 0, 1 etc. from getItemViewType() this will fail with a helpful exception, rather than using the wrong data.
        */
        val value: Int = ordinal + 100
    }

    class InvalidViewTypeException : Exception("Adapters used in ${CompoundAdapter::class.java.simpleName} must override getItemViewType() to return a value from ${ViewType::class.java.simpleName}")

    private val adapters = mutableListOf<RecyclerView.Adapter<RecyclerView.ViewHolder>>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (ViewType.values().filter { it.value == viewType }.size != 1) {
            throw InvalidViewTypeException()
        }

        adapters.forEach { adapter ->
            for (i in 0..adapter.itemCount) {
                val adapterViewType = adapter.getItemViewType(i)
                if (adapterViewType == viewType) {
                    return adapter.onCreateViewHolder(parent, viewType)
                }
            }
        }
        throw InvalidViewTypeException()
    }

    override fun getItemCount(): Int {
        return adapters.fold(0) { total, next -> total + next.itemCount }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var startIndex = 0
        adapters.forEach { adapter ->
            val endIndex = startIndex + adapter.itemCount
            if (position in startIndex until endIndex) {
                val adapterIndex = position - startIndex
                adapter.onBindViewHolder(holder, adapterIndex)
                return
            }
            startIndex = endIndex
        }
    }

    override fun getItemViewType(position: Int): Int {
        var startIndex = 0
        adapters.forEach { adapter ->
            val endIndex = startIndex + adapter.itemCount
            if (position in startIndex until endIndex) {
                val adapterIndex = position - startIndex
                return adapter.getItemViewType(adapterIndex)
            }
            startIndex = endIndex
        }
        throw InvalidViewTypeException()
    }

    fun registerAdapter(adapter: RecyclerView.Adapter<*>) {
        adapters.add(adapter as RecyclerView.Adapter<RecyclerView.ViewHolder>)
    }

}