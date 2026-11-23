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
) {
    // Separem i agafem la primera inicial dels 2 primers noms
    val inicials: String
        get() = nom.trim() // Retallem els espais al principi i final de l'string
            .split("\\s+".toRegex()) // Dividim el text en array tenint en compte espais i tabulacions
            .filter { it.isNotBlank() } // Filtrem elements buits o amb espais formats a l'split
            .take(2) // Agafem 2 primeres paraules (nom i primer cognom)
            .mapNotNull { it.firstOrNull() } // Agafem la inicial d'aquestes 2 paraules. Si només n'hi ha 1 agafa aquesta i null. Despres mapNotNull filtra els valors null
            .joinToString("") // Unifiquem el resultat en un String
            .uppercase() // Ho passem a majuscules

    // Adreça completa
    val adreçaCompleta: String
        get() = listOf(carrer, ciutat, codiPostal) // Creem una llista amb els 3 camps
            .map { it.trim() } // Netejem espais
            .filter { it.isNotBlank() } // Filtrem elements buits o amb espais
            .joinToString(", ") // Unifiquem el resultat en un String i els separem amb una coma

    // Url de l'avatar
    val urlAvatar: String
        get() = "https://i.pravatar.cc/300?u=$username"

    companion object {
        fun desDeJson(json: JSONObject): Contacte {
            val adreca = json.getJSONObject("address")
            val geo = adreca.getJSONObject("geo")
            return Contacte(
                id = json.getInt("id"),
                nom = json.getString("name"),
                username = json.getString("username"),
                email = json.getString("email"),
                telefon = json.getString("phone"),
                web = json.getString("website"),
                carrer = adreca.getString("street"),
                ciutat = adreca.getString("city"),
                codiPostal = adreca.getString("zipcode"),
                latitud = geo.optString("lat").toDoubleOrNull() ?: 0.0,
                longitud = geo.optString("lng").toDoubleOrNull() ?: 0.0
            )
        }
    }
}