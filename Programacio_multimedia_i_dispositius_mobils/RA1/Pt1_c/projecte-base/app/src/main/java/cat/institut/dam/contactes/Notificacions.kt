package cat.institut.dam.contactes

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

object Notificacions {

    private const val CANAL_ID = "contactes_carregats"
    private const val  NOTIFICACIO_ID = 1

    fun mostraContactesCarregats(context: Context, quantitat: Int) {

        // Creem el canal de notificacions (necessari a partir d'Android 8.0)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val canal = NotificationChannel(
                CANAL_ID,
                context.getString(R.string.canal_notificacions),
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = context.getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(canal)
        }

        // Comprovem si l'usuari te les notificacions activades per evitar errors
        val notificationManager = NotificationManagerCompat.from(context)
        if (!notificationManager.areNotificationsEnabled()) return

        // Construim i llancem la notificacio
        val notificacio = NotificationCompat.Builder(context, CANAL_ID)
            .setSmallIcon(R.drawable.ic_notificacio)
            .setContentTitle(context.getString(R.string.notificacio_titol))
            .setContentText(context.getString(R.string.notificacio_text, quantitat))
            .setAutoCancel(true)
            .build()

        try {
            notificationManager.notify(NOTIFICACIO_ID, notificacio)
        } catch (e: SecurityException) {
            // Si falta el permis en temps d'exexuxió, no cau l'app
        }
    }
}