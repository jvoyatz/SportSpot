package gr.jvoyatz.core.common_android.adapters

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import gr.jvoyatz.core.common_android.adapters.RecyclerViewConfig.pool

object AdapterItemTypeConstants {
    const val LOADING = "1"
    const val ITEM = "2"
    const val ERROR = "-1"
}

internal object RecyclerViewConfig{
    val pool = RecyclerView.RecycledViewPool().apply {
      //  setMaxRecycledViews(AdapterItemTypeConstants.LOADING.toInt(), 1)
      //  setMaxRecycledViews(AdapterItemTypeConstants.ITEM.toInt(), 20)
    }
}
fun RecyclerView.setup(adapter: RecyclerView.Adapter<*>, linearLayoutManager: LinearLayoutManager){
    //this.setItemViewCacheSize(20)
    this.layoutManager = linearLayoutManager.apply {
        this.recycleChildrenOnDetach = true
    }
    setRecycledViewPool(pool)
    this.adapter = adapter
}