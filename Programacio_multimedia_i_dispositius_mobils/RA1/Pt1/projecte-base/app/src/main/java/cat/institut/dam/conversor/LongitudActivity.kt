package cat.institut.dam.conversor

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class LongitudActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_longitud)

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
                R.id.radioQuilometre -> "km"
                R.id.radioMilla -> "mi"
                R.id.radioYarda -> "yd"
                else -> "km"
            }

            // Agafem la unitat de desti seleccionada
            val idDestiChecked = radioGroupA.checkedRadioButtonId
            val unitatDesti = when (idDestiChecked) {
                R.id.radioQuilometreA -> "km"
                R.id.radioMillaA -> "mi"
                R.id.radioYardaA -> "yd"
                else -> "mi"
            }

            // Control d'errors per si l'usuari agafa la mateixa unitat
            if (unitatOrigen == unitatDesti) {
                Toast.makeText(this, getString(R.string.error_unitats), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            try {

                // Fem la conversio
                val resultat = convertirLongitud(valor, unitatOrigen, unitatDesti)

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
    private fun convertirLongitud(valor: Double, origen: String, desti: String): Double {

        // Convertim el valor a Quilometres
        val km = when (origen) {
            "km" -> valor
            "mi" -> valor * 1.60934
            "yd" -> valor * 0.0009144
            else -> valor
        }

        // Convertim de Quilometres a la unitat triada
        return when (desti) {
            "km" -> km
            "mi" -> km / 1.60934
            "yd" -> km / 0.0009144
            else -> km
        }
    }
}