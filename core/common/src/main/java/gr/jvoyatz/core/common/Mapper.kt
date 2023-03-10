package gr.jvoyatz.core.common

interface Mapper<From, To> {
    fun From.mapToDomainModel(): To
    fun To.mapFromDomainModel(): From
}

inline fun <I, O> List<I>.mapList(mapSingle: (I) -> O): List<O> {
    return this.map { mapSingle(it) }
}