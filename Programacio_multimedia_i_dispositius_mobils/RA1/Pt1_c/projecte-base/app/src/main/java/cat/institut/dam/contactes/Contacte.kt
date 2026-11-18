package cat.institut.dam.contactes

data class Contacte (
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
)
{
    // Separem i agafem la primera inicial dels 2 primers noms (nom i primer cognom)
    val inicials: String
        get() = nom.split(" ").map { it.first() }.take(2).joinToString("").uppercase()

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
                telefon = json.getString("telefon"),
                web = json.getString("web"),
                carrer = adreça.getString("carrer"),
                ciutat = adreça.getString("ciutat"),
                codiPostal = adreça.getString("codiPostal"),
                latitud = geo.getDouble("latitud"),
                longitud = geo.getDouble("longitud")
            )
        }
    }
}