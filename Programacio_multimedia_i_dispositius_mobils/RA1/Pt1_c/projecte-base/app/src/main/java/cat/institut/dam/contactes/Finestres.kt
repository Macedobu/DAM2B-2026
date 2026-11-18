package cat.institut.dam.contactes

import android.app.Activity
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.Rect
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

/**
 * Finestres — adapta el contingut a les barres del sistema.
 *
 * Amb `targetSdk 35` Android 15 dibuixa l'aplicació darrere de la barra
 * d'estat i de la barra de navegació. Aquest objecte aplica els insets com a
 * padding de la vista arrel perquè cap control quedi tapat.
 */
object Finestres {

    @Suppress("DEPRECATION")
    fun aplica(activity: Activity, arrel: View) {
        WindowCompat.setDecorFitsSystemWindows(activity.window, false)

        // El fons de l'app és el que es veu darrere de les barres.
        activity.window.statusBarColor = Color.TRANSPARENT
        activity.window.navigationBarColor = Color.TRANSPARENT

        val paddingInicial = Rect(
            arrel.paddingLeft,
            arrel.paddingTop,
            arrel.paddingRight,
            arrel.paddingBottom
        )

        ViewCompat.setOnApplyWindowInsetsListener(arrel) { vista, insets ->
            val barres = insets.getInsets(
                WindowInsetsCompat.Type.systemBars() or
                    WindowInsetsCompat.Type.displayCutout()
            )
            val teclat = insets.getInsets(WindowInsetsCompat.Type.ime())
            vista.setPadding(
                paddingInicial.left + barres.left,
                paddingInicial.top + barres.top,
                paddingInicial.right + barres.right,
                paddingInicial.bottom + maxOf(barres.bottom, teclat.bottom)
            )
            insets
        }

        val controlador = WindowInsetsControllerCompat(activity.window, arrel)
        val fosc = (activity.resources.configuration.uiMode and
            Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
        controlador.isAppearanceLightStatusBars = !fosc
        controlador.isAppearanceLightNavigationBars = !fosc
    }
}
