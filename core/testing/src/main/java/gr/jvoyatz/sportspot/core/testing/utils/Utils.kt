package gr.jvoyatz.sportspot.core.testing.utils

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import gr.jvoyatz.common.testing.MainDispatcherRule
import okhttp3.OkHttpClient

/**
 * For internal use only, loads the content of files located in the resources directory.
 *
 * for a file file.json, pass "/sport_events.json"
 */
fun Any.loadResourceFile(fileName: String): String =
    this.javaClass.getResourceAsStream(fileName)?.
    bufferedReader().use { it!!.readText() }



object Utils {
    val coroutineRule: MainDispatcherRule = MainDispatcherRule()
    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    val okHttpClient = OkHttpClient.Builder().build()
}