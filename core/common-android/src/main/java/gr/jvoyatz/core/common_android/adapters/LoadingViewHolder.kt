package gr.jvoyatz.core.common_android.adapters

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class LoadingViewHolder<T: ViewBinding>(private val binding: T): RecyclerView.ViewHolder(binding.root){
    fun bind(block : (T: ViewBinding) -> Unit){
        block(binding)
    }
}