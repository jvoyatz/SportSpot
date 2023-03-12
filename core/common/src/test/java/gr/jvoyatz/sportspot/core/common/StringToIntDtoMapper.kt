package gr.jvoyatz.sportspot.core.common

object StringToIntDtoMapper : DtoMapper<String, Int> {
    override fun String.dtoToDomain(): Int = this.toInt()

    override fun Int.domainToDto(): String = this.toString()
}