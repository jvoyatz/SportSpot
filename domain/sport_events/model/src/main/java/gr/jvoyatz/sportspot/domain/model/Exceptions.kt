package gr.jvoyatz.sportspot.domain.model

/**
 * A custom exception for handling error cases during fetching of sport events
 */
sealed class SportEventException: RuntimeException{
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
    constructor(cause: Throwable) : super(cause)

    class ErrorException(message: String = ""):SportEventException(message)
    class NetworkException(message:String = ""):SportEventException(message)
    class UnknownException(message:String = ""):SportEventException(message)
    class HttpException(message:String = ""):SportEventException(message)
}

