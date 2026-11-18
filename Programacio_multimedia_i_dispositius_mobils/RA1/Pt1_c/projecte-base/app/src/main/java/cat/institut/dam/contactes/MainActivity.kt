package cat.institut.dam.contactes

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

/**
 * MainActivity — la llista de contactes.
 *
 * Aquesta classe és l'esquelet de la pràctica: la interfície ja està
 * connectada, però la part de les dades (carregar, filtrar i obrir el
 * detall) la has de fer tu. Busca els TODO.
 */
class MainActivity : AppCompatActivity() {

    companion object {
        /** Codi de la petició de permís de notificacions. */
        private const val REQUEST_NOTIFICACIONS = 100
    }

    private lateinit var etCerca: EditText
    private lateinit var btnActualitza: Button
    private lateinit var pbCarga: ProgressBar
    private lateinit var txtBuit: TextView
    private lateinit var txtAvis: TextView
    private lateinit var rvContactes: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Finestres.aplica(this, findViewById<View>(R.id.arrel))

        etCerca = findViewById(R.id.etCerca)
        btnActualitza = findViewById(R.id.btnActualitza)
        pbCarga = findViewById(R.id.pbCarga)
        txtBuit = findViewById(R.id.txtBuit)
        txtAvis = findViewById(R.id.txtAvis)
        rvContactes = findViewById(R.id.rvContactes)

        // TODO 1: crea el teu ContacteAdapter, assigna'l al RecyclerView i
        //         posa-li un LinearLayoutManager.
        //
        //         rvContactes.layoutManager = LinearLayoutManager(this)
        //         rvContactes.adapter = adapter

        // TODO 2: el cercador ha de filtrar la llista a cada tecla.
        //
        //         etCerca.doAfterTextChanged { text -> ... }

        // TODO 3: el botó ha de tornar a carregar els contactes.
        //
        //         btnActualitza.setOnClickListener { ... }

        // TODO 4: el mètode demanaPermisNotificacions() ja està implementat.
        //         Crida'l quan hagis carregat les dades (TODO 5), abans de
        //         mostrar la notificació, i gestiona la denegació (TODO 6).
        carrega()
    }

    /**
     * TODO 5: carrega els contactes.
     *
     * La petició HTTP no es pot fer al fil principal: has de fer-la en un
     * fil de segon pla i tornar al fil principal amb un Handler.
     *
     * Si no hi ha xarxa, carrega els contactes del fitxer local
     * `assets/contactes.json` i avisa l'usuari amb `txtAvis`.
     *
     * Quan la llista s'hagi carregat correctament, crida
     * `demanaPermisNotificacions()` abans de mostrar la notificació.
     */
    private fun carrega() {
        // TODO: implementa la càrrega de dades
    }

    /**
     * A partir d'Android 13 (API 33) cal demanar permís en temps d'execució
     * per poder mostrar notificacions. En versions anteriors el permís no
     * existeix i no cal demanar-lo.
     */
    private fun demanaPermisNotificacions() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return
        val permissio = Manifest.permission.POST_NOTIFICATIONS
        if (ContextCompat.checkSelfPermission(this, permissio) == PackageManager.PERMISSION_GRANTED) {
            return
        }
        requestPermissions(arrayOf(permissio), REQUEST_NOTIFICACIONS)
    }

    /**
     * Resposta de l'usuari a la petició de permís: si el denega, avisa'l amb
     * un Toast.
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_NOTIFICACIONS) {
            val concedit = grantResults.isNotEmpty() &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED
            if (!concedit) {
                // TODO 6: avisa l'usuari que no es mostraran notificacions
            }
        }
    }
}
