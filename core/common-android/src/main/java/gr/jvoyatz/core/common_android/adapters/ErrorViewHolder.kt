package gr.jvoyatz.core.common_android.adapters

import androidx.recyclerview.widget.RecyclerView
import gr.jvoyatz.sportspot.core.common_android.databinding.ListItemErrorBinding

class ErrorViewHolder(
    private val binding: ListItemErrorBinding,
    message: String?= null,
    errorAction: ((Int) -> Unit)? = null,
): RecyclerView.ViewHolder(binding.root){

    var setTextFunc: (String) ->Unit = {
        it.let {
            binding.errorItem.text = message
        }
    }

    init {
        errorAction?.also {action ->
            binding.errorButton.setOnClickListener{
                action(bindingAdapterPosition)
            }
        }
        message?.let(setTextFunc)
    }

    fun bind(message: String?=null){
        message?.let(setTextFunc)
    }
}