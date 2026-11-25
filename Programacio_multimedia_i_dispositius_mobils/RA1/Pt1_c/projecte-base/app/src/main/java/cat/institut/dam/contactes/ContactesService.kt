package cat.institut.dam.contactes

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL

object ContactesService {

    // Declarem l'adreça d'on treurem els contactes
    const val URL_CONTACTES = "https://jsonplaceholder.typicode.com/users"

    // Metode per realitzar la connexio
    private fun realitzarConnexio(url: String): HttpURLConnection {
        val connexio = URL(url).openConnection() as HttpURLConnection
        try {
            connexio.requestMethod = "GET"
            connexio.connectTimeout = 8000   // espera màxima per connectar
            connexio.readTimeout = 8000      // espera màxima per rebre

            if (connexio.responseCode != HttpURLConnection.HTTP_OK) {
                connexio.disconnect()
                throw IllegalStateException("El servidor ha respost amb ${connexio.responseCode}")
            }
            return connexio
        } catch (e: Exception) {
            connexio.disconnect()
            throw e
        }
    }

    // Parsejem el json que agafem de l'adreça
    fun parseja(text: String): List<Contacte> {
        val array = JSONArray(text) // Convertim el text en una array JSON
        val resultat = mutableListOf<Contacte>() // Variable on guardarem la llista ja parsejada
        for (i in 0 until array.length()) { // until = .length()-1
            // Cridem el metode desDeJson de Contacte per parsejar i guardem el contacte a la llista
            resultat.add(Contacte.desDeJson(array.getJSONObject(i)))
        }
        return resultat
    }

    // Obrim la copia local assets/contactes.json quan falli internet
    fun desDeRecurs(context: Context): List<Contacte> {
        val contingut = context.assets.open("contactes.json") // Obrim el fitxer
            .bufferedReader().use { it.readText() } // En llegim el text
        return parseja(contingut) // Fem servir el metode parseja per retornar la llista de contactes
    }

    // Ens conectem a la web per recollir els contactes
    fun desDeXarxa(): List<Contacte> {
        val connexio = realitzarConnexio(URL_CONTACTES)
        try {
            val text = connexio.inputStream.bufferedReader().use { it.readText() }
            return parseja(text) // Parsejem i retornem les dades
        } finally { connexio.disconnect() }
    }

    // Baixem una imatge de la web
    fun baixaImatge(url: String?): Bitmap? {
        if (url.isNullOrBlank()) return null
        try { // Si la conexió falla retornarem null
            val connexio = realitzarConnexio(url)
            try {
                val bitmap = connexio.inputStream.use { BitmapFactory.decodeStream(it) }
                return bitmap
            } finally { connexio.disconnect() }
        } catch (e: Exception) { return null }
    }
}