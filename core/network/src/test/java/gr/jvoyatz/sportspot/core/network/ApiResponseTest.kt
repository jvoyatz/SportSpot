package gr.jvoyatz.sportspot.core.network

import com.google.common.truth.Truth
import kotlinx.coroutines.test.runTest
import org.junit.Test

class ApiResponseTest {

    val success = ApiResponse.success<String, Unit>("234")
    val successEmpty = ApiResponse.success<Unit, Unit>(Unit)
    val httpError = ApiResponse.httpError<String, String>(234, "http error")
    val unknownError = ApiResponse.unknownError<String, String>(NullPointerException("unknownerror"))
    val networkError = ApiResponse.networkError<String, String>(IllegalStateException("networkerror"))

    @Test
    fun `test that httpError() returns HttpError instance`(){
        //given
        val code = 1
        val bodyStr = "error"

        //when
        val httpError = ApiResponse.httpError<Unit, String>(code, bodyStr)

        //then
        Truth.assertThat(httpError).isInstanceOf(HttpError::class.java)
    }

    @Test
    fun `test that unknownError() returns unknownError instance`(){
        //given
        val throwable = IllegalStateException()

        //when
        val unknownError = ApiResponse.unknownError<Any, Any>(throwable)

        //then
        Truth.assertThat(unknownError).isInstanceOf(UnknownError::class.java)
    }

    @Test
    fun `test that networkError() returns networkError instance`(){
        //given
        val throwable = RuntimeException()

        //when
        val networkError = ApiResponse.networkError<Any, Any>(throwable)

        //then
        Truth.assertThat(networkError).isInstanceOf(NetworkError::class.java)
    }

    @Test
    fun `test that success() returns success instance`(){
        //given
        val data = "1"

        //when
        val success = ApiResponse.success<String, Unit>(data)

        //then
        Truth.assertThat(success).isInstanceOf(ApiSuccess::class.java)
    }

    @Test
    fun `asSuccess returns nonNull when success instance`(){
        //given
        val data = "1"

        //when
        val success = ApiResponse.success<String, Unit>(data)

        //then
        Truth.assertThat(success.isSuccess()).isTrue()
        Truth.assertThat(success.asSuccess()).isNotNull()
    }

    @Test
    fun `asSuccess returns null when non success instance`(){
        //when
        val success = ApiResponse.networkError<String, Unit>(null)

        //then
        Truth.assertThat(success.asSuccess()).isNull()
    }

    @Test
    fun `asError returns nonNull when error instance`(){
        //when
        val networkError = ApiResponse.networkError<String, Unit>(null)

        //then
        Truth.assertThat(networkError.asError()).isNotNull()
    }

    @Test
    fun `asError returns null when success instance`(){
        //when
        val success = ApiResponse.success<String, Unit>("234")

        //then
        Truth.assertThat(success.asError()).isNull()
    }

    @Test
    fun `asHttpError returns nonNull when HttpError instance`(){
        //when
        val httpError = ApiResponse.httpError<String, Unit>(1, Unit)

        //then
        Truth.assertThat(httpError.asHttpError()).isNotNull()
    }

    @Test
    fun `asHttpError returns null when other instance`(){
        //when
        val success = ApiResponse.success<String, Unit>("234")

        //then
        Truth.assertThat(success.asHttpError()).isNull()
    }

    @Test
    fun `asNetworkError returns nonNull when NetworkError instance`(){
        //when
        val networkError = ApiResponse.networkError<String, Unit>(null)

        //then
        Truth.assertThat(networkError.asNetworkError()).isNotNull()
    }

    @Test
    fun `asNetworkError returns null in case of another instance`(){
        //when
        val success = ApiResponse.success<String, Unit>("234")

        //then
        Truth.assertThat(success.asNetworkError()).isNull()
    }

    @Test
    fun `asUnknownError returns nonNull when UnknownError instance`(){
        //when
        val unknownError = ApiResponse.unknownError<String, Unit>(null)

        //then
        Truth.assertThat(unknownError.asUnknownError()).isNotNull()
    }

    @Test
    fun `asUnknownError returns null when non UnknownError instance`(){
        //when
        val success = ApiResponse.success<String, Unit>("234")

        //then
        Truth.assertThat(success.asUnknownError()).isNull()
    }

    @Test
    fun `isSuccess returns true when ApiSuccess instance`(){
        //when
        val isSuccess = success.isSuccess()

        //then
        Truth.assertThat(isSuccess).isTrue()
    }
    @Test
    fun `isSuccess returns false when non ApiSuccess instance`(){
        //when
        val isSuccess = httpError.isSuccess()

        //then
        Truth.assertThat(isSuccess).isFalse()
    }

    @Test
    fun `isSuccessEmpty returns true when ApiSuccess and empty body instance`(){
        //when
        val isEmptySuccess = successEmpty.isSuccessEmpty()

        //then
        Truth.assertThat(isEmptySuccess).isTrue()
    }
    @Test
    fun `isSuccessEmpty returns false when  ApiSuccess and other type body instance`(){
        //when
        val isEmptySuccess = success.isSuccessEmpty()

        //then
        Truth.assertThat(isEmptySuccess).isFalse()
    }
    @Test
    fun `isSuccessEmpty returns false when non  ApiSuccess`(){
        //when
        val isEmptySuccess = httpError.isSuccessEmpty()

        //then
        Truth.assertThat(isEmptySuccess).isFalse()
    }

    @Test
    fun `isError returns true when HttpError instance`(){
        //when
        val isError = httpError.isError()

        //then
        Truth.assertThat(isError).isTrue()
    }
    @Test
    fun `isError returns true when any ApiError instance`(){
        //when
        val isError = unknownError.isError()

        //then
        Truth.assertThat(isError).isTrue()
    }

    @Test
    fun `isError returns false when ApiSuccess instance`(){
        //when
        val isError = success.isError()

        //then
        Truth.assertThat(isError).isFalse()
    }

    @Test
    fun `getOrNull returns not null data when ApiSuccess`(){
        //given
        val body = "234"

        //when
        val data = success.getOrNull()

        //then
        Truth.assertThat(data).isNotNull()
        Truth.assertThat(data).isEqualTo(body)
    }

    @Test
    fun `getOrNull returns null data when any other instance`(){
        //when
        val data = httpError.getOrNull()

        //then
        Truth.assertThat(data).isNull()
    }

    @Test
    fun `onSuccess is executed when apiResponse is of an ApiSuccess instance`(){
        //given
        var isExecuted = false

        //when
        success.onSuccess {
            isExecuted = true
        }

        //then
        Truth.assertThat(isExecuted).isTrue()
    }

    @Test
    fun `onSuccess is not executed when apiResponse is not an ApiSuccess instance`(){
        //given
        var isExecuted = false

        //when
        httpError.onSuccess {
            isExecuted = true
        }

        //then
        Truth.assertThat(isExecuted).isFalse()
    }

    @Test
    fun `onSuspendedSuccess is executed when apiResponse is of an ApiSuccess instance`() = runTest {
        //given
        var isExecuted = false

        //when
        success.onSuspendedSuccess {
            isExecuted = true
        }

        //then
        Truth.assertThat(isExecuted).isTrue()
    }

    @Test
    fun `onSuspendedSuccess is not executed when apiResponse is not an ApiSuccess instance`() = runTest {
        //given
        var isExecuted = false

        //when
        httpError.onSuspendedSuccess {
            isExecuted = true
        }

        //then
        Truth.assertThat(isExecuted).isFalse()
    }
}