package cat.institut.dam.conversor

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class MassaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_massa)

        // Enllacem els elements del layout amb variables
        val editValor = findViewById<EditText>(R.id.editValor)
        val radioGroupDe = findViewById<RadioGroup>(R.id.radioGroupDe)
        val radioGroupA = findViewById<RadioGroup>(R.id.radioGroupA)
        val botoConvertir = findViewById<Button>(R.id.botoConvertir)
        val textResultat = findViewById<TextView>(R.id.textResultat)

        // Programem botó convertir
        botoConvertir.setOnClickListener {
            val textValor = editValor.text.toString().trim()

            // Control d'errors per si l'usuari no escriu res
            if (textValor.isEmpty()) {
                Toast.makeText(this, getString(R.string.error_buid), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Control d'errors per si l'usuari escriu un valor no vàlid
            val valor = textValor.toDoubleOrNull() // Si el valor no es vàlid retorna Null
            if (valor == null) {
                Toast.makeText(this, getString(R.string.error_numeric), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Agafem la unitat d'origen seleccionada
            val idOrigenChecked = radioGroupDe.checkedRadioButtonId
            val unitatOrigen = when (idOrigenChecked) {
                R.id.radioQuilogram -> "kg"
                R.id.radioLliura -> "lb"
                R.id.radioUnca -> "oz"
                else -> "kg"
            }

            // Agafem la unitat de desti seleccionada
            val idDestiChecked = radioGroupA.checkedRadioButtonId
            val unitatDesti = when (idDestiChecked) {
                R.id.radioQuilogramA -> "kg"
                R.id.radioLliuraA -> "lb"
                R.id.radioUncaA -> "oz"
                else -> "lb"
            }

            // Control d'errors per si l'usuari agafa la mateixa unitat
            if (unitatOrigen == unitatDesti) {
                Toast.makeText(this, getString(R.string.error_unitats), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            try {
                // Fem la conversio
                val resultat = convertirMassa(valor, unitatOrigen, unitatDesti)

                // Formatem el text i el mostrem
                val textFormatat =
                    String.format(Locale.US, "Resultat: %.2f %s", resultat, unitatDesti)
                textResultat.text = textFormatat

            }catch (e: Exception) {

            // Control d'errors per si el calcul falla per qualsevol altre motiu
            Toast.makeText(this, getString(R.string.error_generic), Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Metode per calcular la conversio
    private fun convertirMassa(valor: Double, origen: String, desti: String): Double {

        // Convertim el valor a Quilograms
        val kg = when (origen) {
            "kg" -> valor
            "lb" -> valor * 0.45359237
            "oz" -> valor * 0.02834952
            else -> valor
        }

        // Convertim de Quilograms a la unitat triada
        return when (desti) {
            "kg" -> kg
            "lb" -> kg / 0.45359237
            "oz" -> kg / 0.02834952
            else -> kg
        }
    }
}