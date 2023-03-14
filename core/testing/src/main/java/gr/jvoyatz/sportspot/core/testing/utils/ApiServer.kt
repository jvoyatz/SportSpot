package gr.jvoyatz.sportspot.core.testing.utils

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.SocketPolicy
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@RunWith(JUnit4::class)
abstract class ApiServer<T: Any> {
    protected lateinit var clazz: Class<T>
    protected lateinit var webServer: MockWebServer
    protected lateinit var apiClient: T

    @get:Rule
    val rule: MainDispatcherRule = Utils.coroutineRule
    private val okHttpClient = Utils.okHttpClient
    private val moshi = Utils.moshi

    @Before
    open fun setupServer(){
        webServer = MockWebServer()
        webServer.start()

        apiClient = Retrofit.Builder()
            .baseUrl(webServer.url("/"))
            .client(okHttpClient)
            //Use JsonReader.setLenient(true) to accept malformed JSON at path
            .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
            .build()
            .create(clazz)
    }

    @After
    fun stopServer(){
        webServer.shutdown()
    }

    protected fun loadFileAndEnqueue(fileName: String, block: () -> MockResponse){
        with(fileName) {
            loadResourceFile(this).run {
                enqueue {
                    block()
                }
            }
        }
    }
    protected fun enqueue(block: ()-> MockResponse){
        webServer.enqueue(block())
    }

    protected fun success(data: String):MockResponse = MockResponse()
            .setBody(data)
            .setResponseCode(200)

    protected fun httpError():MockResponse = MockResponse()
        .setBody(RetrofitMockData.ERROR_RESPONSE)
        .setResponseCode(400)

    protected fun networkError():MockResponse = MockResponse()
        .setSocketPolicy(SocketPolicy.DISCONNECT_AFTER_REQUEST)

    protected fun unknownError():MockResponse = MockResponse()
        .setBody("this is not an unknown error, but a deserialization exception")
        .setResponseCode(200)
}