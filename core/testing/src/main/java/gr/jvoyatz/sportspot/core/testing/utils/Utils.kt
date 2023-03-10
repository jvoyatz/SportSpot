package gr.jvoyatz.sportspot.core.testing.utils

/**
 * For internal use only, loads the content of files located in the resources directory.
 *
 * for a file file.json, pass "/sport_events.json"
 */
internal fun String.loadResourceFile(fileName: String): String =
    this.javaClass.getResourceAsStream(fileName)?.
    bufferedReader().use { it!!.readText() }

/**
 * Loads the content of this json file
 */
fun loadSportEventsData(): String = "/sport_events.json".run { loadResourceFile(this) }