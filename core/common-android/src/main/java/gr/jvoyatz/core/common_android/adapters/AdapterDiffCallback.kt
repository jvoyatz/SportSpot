package gr.jvoyatz.core.common_android.adapters

import androidx.recyclerview.widget.DiffUtil

abstract class AdapterDiffCallback<T : Any>  : DiffUtil.ItemCallback<T>(){

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return isSameItem(oldItem, newItem)
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return isContentSame(oldItem, newItem)
    }

    abstract fun isSameItem(oldItem: T, newItem: T): Boolean
    abstract fun isContentSame(oldItem: T, newItem: T): Boolean
}