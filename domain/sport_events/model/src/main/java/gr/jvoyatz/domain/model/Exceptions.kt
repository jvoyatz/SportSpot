package gr.jvoyatz.domain.model

/**
 * A custom exception for handling error cases during fetching of sport events
 */
class SportEventException: RuntimeException{
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
    constructor(cause: Throwable) : super(cause)
}