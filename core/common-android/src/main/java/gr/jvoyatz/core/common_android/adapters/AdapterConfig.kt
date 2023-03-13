package gr.jvoyatz.core.common_android.adapters

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import gr.jvoyatz.core.common_android.adapters.RecyclerViewConfig.pool

object AdapterItemTypeConstants {
    const val LOADING = "1"
    const val ITEM = "2"
    const val ERROR = "-1"
    const val EMPTY = "0"

    const val TYPE_LOADING = 1
    const val TYPE_ITEM = 2
    const val TYPE_ERROR = -1
    const val TYPE_EMPTY = 0
}

internal object RecyclerViewConfig{
    val pool = RecyclerView.RecycledViewPool().apply {
//        setMaxRecycledViews(AdapterItemTypeConstants.TYPE_ERROR, 1)
//        setMaxRecycledViews(AdapterItemTypeConstants.TYPE_EMPTY, 1)
//        setMaxRecycledViews(AdapterItemTypeConstants.TYPE_LOADING, 1)
//        setMaxRecycledViews(AdapterItemTypeConstants.TYPE_ITEM, 20)
    }
}
fun RecyclerView.setup(adapter: RecyclerView.Adapter<*>, linearLayoutManager: LinearLayoutManager){
    //this.setItemViewCacheSize(20)
//    .apply {
//        this.recycleChildrenOnDetach = true
//    }
    //setRecycledViewPool(pool)
    this.layoutManager = linearLayoutManager
    this.adapter = adapter
}