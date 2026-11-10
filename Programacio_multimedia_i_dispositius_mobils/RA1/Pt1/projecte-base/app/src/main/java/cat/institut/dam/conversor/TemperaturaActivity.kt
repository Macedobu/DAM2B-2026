package cat.institut.dam.conversor

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

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
            val textValor = editValor.text.toString()

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

            // Fem la conversio
            val resultat = convertirTemperatura(valor, unitatOrigen, unitatDesti)

            // Mostrem el resultat
            textResultat.text = "Resultat: $resultat $unitatDesti"
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