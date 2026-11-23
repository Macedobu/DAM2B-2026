package cat.institut.dam.contactes

// Importem Uri per poder guardar les rutes de les fotos
import android.net.Uri

// object per crear un singleton
object ContactesRepository {
    private val contactes = mutableListOf<Contacte>() // Creem una llista interna modifiable on es guardaran els contactes carregats

    // Llista (per consultar els contactes)
    val llista: List<Contacte>
        get() = contactes

    // Contacte (Busquem contactes per id)
    fun contacte(id: Int): Contacte? { // ? perque pot ser null (no troba el contacte)
        return contactes.firstOrNull { it.id == id } // Retornem la primera id que coincideix, o null si no coincideix
    }

    fun actualitza(novs: List<Contacte>) {
        contactes.clear()
        contactes.addAll(novs)
    }

    private val favorits = mutableSetOf<Int>()

    fun esFavorit(id: Int): Boolean {
        return id in favorits
    }

    fun alternaFavorit(id: Int) {
        if (!favorits.add(id)) {
            favorits.remove(id)
        }
    }

    fun cerca(texte: String): List<Contacte> {
        if (texte.isBlank()) return contactes.toList()
        return contactes.filter {
            it.nom.contains(texte, ignoreCase = true) ||
                    it.ciutat.contains(texte, ignoreCase = true) ||
                    it.email.contains(texte, ignoreCase = true) }
    }

    private val fotos = mutableMapOf<Int, Uri>()

    fun desaFoto(id: Int, uri: Uri) {
        fotos[id] = uri
    }

    fun foto(id: Int): Uri? {
        return fotos[id]
    }
}