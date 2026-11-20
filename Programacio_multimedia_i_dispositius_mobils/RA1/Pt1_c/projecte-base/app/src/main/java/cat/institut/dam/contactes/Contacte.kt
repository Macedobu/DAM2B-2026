package cat.institut.dam.contactes

import org.json.JSONObject

data class Contacte(
    val id: Int,
    val nom: String,
    val username: String,
    val email: String,
    val telefon: String,
    val web: String,
    val carrer: String,
    val ciutat: String,
    val codiPostal: String,
    val latitud: Double,
    val longitud: Double
){
    // Separem i agafem la primera inicial dels 2 primers noms
    val inicials: String
        get() = nom.trim() // Retalla els espais al principi i final de l'string
            .split("\\s+".toRegex()) // Divideix el text en array tenint en compte espais i tabulacions
            .filter {it.isNotEmpty()} // Elimina possibles elements buits formats a l'split
            .take(2) // Agafa 2 primeres paraules (nom i primer cognom)
            .map { it.first() } // agafa la inicial d'aquestes 2 paraules
            .joinToString("") // Unifica el resultat en un String
            .uppercase() // Ho passa a majuscules

    // Adreça completa
    val adreçaCompleta: String
        get() = "$carrer, $ciutat, $codiPostal"

    // Url de l'avatar
    val urlAvatar: String
        get() = "https://i.pravatar.cc/300?u=$username"

    companion object {
        fun desDeJson(json: JSONObject): Contacte {
            val adreça = json.getJSONObject("address")
            val geo = adreça.getJSONObject("geo")
            return Contacte(
                id = json.getInt("id"),
                nom = json.getString("name"),
                username = json.getString("username"),
                email = json.getString("email"),
                telefon = json.getString("phone"),
                web = json.getString("website"),
                carrer = adreça.getString("street"),
                ciutat = adreça.getString("city"),
                codiPostal = adreça.getString("zipcode"),
                latitud = geo.optString("lat").toDoubleOrNull() ?: 0.0,
                longitud = geo.optString("lng").toDoubleOrNull() ?: 0.0
            )
        }
    }
}