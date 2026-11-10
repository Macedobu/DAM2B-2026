package cat.institut.dam.conversor

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class TemperaturaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temperatura)

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
                R.id.radioCelsius -> "C"
                R.id.radioFahrenheit -> "F"
                R.id.radioKelvin -> "K"
                else -> "C"
            }

            // Agafem la unitat de desti seleccionada
            val idDestiChecked = radioGroupA.checkedRadioButtonId
            val unitatDesti = when (idDestiChecked) {
                R.id.radioCelsiusA -> "C"
                R.id.radioFahrenheitA -> "F"
                R.id.radioKelvinA -> "K"
                else -> "F"
            }

            // Control d'errors per si l'usuari agafa la mateixa unitat
            if (unitatOrigen == unitatDesti) {
                Toast.makeText(this, getString(R.string.error_unitats), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            try {
            // Fem la conversio
            val resultat = convertirTemperatura(valor, unitatOrigen, unitatDesti)

            // Formatem el text i el mostrem
            val textFormatat = String.format(Locale.US, "Resultat: %.2f %s", resultat, unitatDesti)
            textResultat.text = textFormatat

            }catch (e: Exception) {

                // Control d'errors per si el calcul falla per qualsevol altre motiu
                Toast.makeText(this, getString(R.string.error_generic), Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Metode per calcular la conversio
    private fun convertirTemperatura(valor: Double, origen: String, desti: String): Double {

        // Convertim el valor a Celsius
        val celsius = when (origen) {
            "C" -> valor
            "F" -> (valor - 32) * 5 / 9
            "K" -> valor - 273.15
            else -> valor
        }

        // Convertim de Celsius a la unitat triada
        return when (desti) {
            "C" -> celsius
            "F" -> celsius * 9 / 5 + 32
            "K" -> celsius + 273.15
            else -> celsius
        }
    }
}