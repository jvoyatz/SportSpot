package gr.jvoyatz.sportspot.core.testing.utils

/**
 * For internal use only, loads the content of files located in the resources directory.
 *
 * for a file file.json, pass "/sport_events.json"
 */
fun Any.loadResourceFile(fileName: String): String =
    this.javaClass.getResourceAsStream(fileName)?.
    bufferedReader().use { it!!.readText() }