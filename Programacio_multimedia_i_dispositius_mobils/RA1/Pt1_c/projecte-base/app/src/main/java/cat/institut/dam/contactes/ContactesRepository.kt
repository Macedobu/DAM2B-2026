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
        contactes.clear() // Esborrem els contactes que hi havia abans
        contactes.addAll(novs) // Afegim els contactes de la llista que rep
    }

    private val favorits = mutableSetOf<Int>() // Creem un objecte mutableSet (no permet valors repetits)

    // Comprovem si un contacte esta marcat com a favorit
    fun esFavorit(id: Int): Boolean {
        return id in favorits
    }

    // Afegim o treiem de favorits la id del contacte que li passem
    fun alternaFavorit(id: Int) {
        if (!favorits.add(id)) {
            favorits.remove(id)
        }
    }

    // Filtrem la llista a partir del text que se li passa (només filtra nom, ciutat o email)
    fun cerca(texte: String): List<Contacte> {
        if (texte.isBlank()) return contactes.toList() // Retornem tots els contactes si el text esta buit o hi ha espais
        return contactes.filter {
            it.nom.contains(texte, ignoreCase = true) ||
                    it.ciutat.contains(texte, ignoreCase = true) ||
                    it.email.contains(texte, ignoreCase = true) }
    }

    private val fotos = mutableMapOf<Int, Uri>() // Creem un mutableMap (diccionari)

    fun desaFoto(id: Int, uri: Uri) { // Desem la Uri de la foto. Si ja existia l'actualitzem
        fotos[id] = uri
    }

    fun foto(id: Int): Uri? { // Retornem la Uri de la foto del contacte seleccionat
        return fotos[id]
    }
}