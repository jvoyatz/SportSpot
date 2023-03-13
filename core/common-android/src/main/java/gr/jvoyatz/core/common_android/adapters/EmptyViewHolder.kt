package gr.jvoyatz.core.common_android.adapters

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class EmptyViewHolder<T: ViewBinding>(binding: T): RecyclerView.ViewHolder(binding.root)