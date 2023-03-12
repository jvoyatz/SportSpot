package gr.jvoyatz.sportspot.core.common

object StringToIntEntityMapper : EntityMapper<String, Int> {
    override fun String.entityToDomain(): Int = this.toInt()

    override fun Int.domainToEntity(): String = this.toString()
}