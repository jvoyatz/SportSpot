package gr.jvoyatz.sportspot.core.common

object StringToIntMapper : Mapper<String, Int>{
    override fun String.mapToDomainModel(): Int = this.toInt()

    override fun Int.mapFromDomainModel(): String = this.toString()
}