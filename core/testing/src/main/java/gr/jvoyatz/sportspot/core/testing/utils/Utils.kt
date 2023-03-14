package gr.jvoyatz.sportspot.core.testing.utils

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import gr.jvoyatz.sportspot.core.common.AppDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import okhttp3.OkHttpClient

/**
 * For internal use only, loads the content of files located in the resources directory.
 *
 * for a file file.json, pass "/sport_events.json"
 */
fun Any.loadResourceFile(fileName: String): String =
    this.javaClass.getResourceAsStream(fileName)?.
    bufferedReader().use { it!!.readText() }



@OptIn(ExperimentalCoroutinesApi::class)
object Utils {

    /**
     * In order to avoid error during tests,
     * all scopes and dispatchers should use the same testScheduler
     */
    //not using a custom scope, so comment out
    //val testScope: TestScope = TestScope(testDispatcher)

    private val testScheduler: TestCoroutineScheduler = TestCoroutineScheduler()
    private val testDispatcher = UnconfinedTestDispatcher(testScheduler)
    val coroutineRule: MainDispatcherRule = MainDispatcherRule(testDispatcher)

    val testDispatchers = object: AppDispatchers {
        override val io: CoroutineDispatcher
            get() = coroutineRule.testDispatcher
        override val main: CoroutineDispatcher
            get() = coroutineRule.testDispatcher
        override val default: CoroutineDispatcher
            get() = coroutineRule.testDispatcher
        override val unconfined: CoroutineDispatcher
            get() = coroutineRule.testDispatcher
    }

    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    val okHttpClient = OkHttpClient.Builder().build()
}

inline fun <reified T> deserializeList(content: String): List<T>? {
    val type = Types.newParameterizedType(List::class.java, T::class.java)
    val moshi = Utils.moshi
    return moshi.adapter<List<T>>(type).fromJson(content).orEmpty()
}