package gr.jvoyatz.sportspot.core.common

interface Mapper<From, To> {
//    fun mapToDomainModel(from: From): To
//    fun mapFromDomainModel(to: To): From
}

interface DtoMapper<From, To> : Mapper<From, To>{
    fun From.dtoToDomain(): To
    fun To.domainToDto(): From
}

interface EntityMapper<From, To> : Mapper<From, To>{
    fun From.entityToDomain(): To
    fun To.domainToEntity(): From
}

inline fun <I, O> List<I>.mapList(mapSingle: (I) -> O): List<O> {
    return this.map { mapSingle(it) }
}
