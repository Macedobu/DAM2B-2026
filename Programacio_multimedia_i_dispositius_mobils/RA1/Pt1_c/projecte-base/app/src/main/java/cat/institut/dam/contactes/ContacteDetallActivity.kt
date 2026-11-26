package cat.institut.dam.contactes

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class ContacteDetallActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "extra_id"
        private const val REQUEST_FOTO = 200
    }

    private lateinit var ivFoto: ImageView
    private val filPrincipal = Handler(Looper.getMainLooper())
    private var contacteId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detall)
        Finestres.aplica(this, findViewById(R.id.arrelDetall))

        // Recuperem l'ID rebut per internet i busquem el contacte al repositori
        contacteId = intent.getIntExtra(EXTRA_ID, -1)
        val contacte = ContactesRepository.contacte(contacteId)

        if (contacte == null) {
            Toast.makeText(this, R.string.error_contacte, Toast.LENGTH_LONG).show()
            finish()
            return
        }

        // Connectem les vistes i omplim les dades
        ivFoto = findViewById(R.id.ivFoto)
        val tvNom: TextView = findViewById(R.id.tvNom)
        val tvEmail: TextView = findViewById(R.id.tvEmail)
        val tvTelefon: TextView = findViewById(R.id.tvTelefon)
        val tvAdreca: TextView = findViewById(R.id.tvAdreca)
        val tvWeb: TextView = findViewById(R.id.tvWeb)
        val chkFavorit: CheckBox = findViewById(R.id.chkFavorit)
    }
}