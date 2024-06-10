package com.cpstn.momee.firebase

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.os.bundleOf
import com.cpstn.momee.MainActivity
import com.cpstn.momee.R
import com.cpstn.momee.data.domain.UserListDomain
import com.cpstn.momee.ui.chat.ChatActivity
import com.cpstn.momee.utils.EXTRAS
import com.cpstn.momee.utils.NOTIFICATION_ACTION
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONObject

class FirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {

        super.onNewToken(token)
    }


    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.data.isNotEmpty()) {
            parseNotificationData(remoteMessage)
        } else {
            super.onMessageReceived(remoteMessage)
        }
    }

    private fun parseNotificationData(remoteMessage: RemoteMessage) {
        val data = remoteMessage.data
        val jsonObject = JSONObject(data as Map<*, *>)
        val title = jsonObject.getString("title")
        val body = jsonObject.getString("body")
        val email = jsonObject.getString("receiver")
        val action = jsonObject.getString("action")
        sendNotification(title, body, email, action)
    }

    private fun sendNotification(
        title: String?,
        messageBody: String?,
        receiver: String,
        action: String?
    ) {
        val contentIntent = handlePendingIntent(action, title, receiver)
        val taskStackBuilder = TaskStackBuilder.create(this).run {
            addParentStack(MainActivity::class.java)
            addNextIntent(contentIntent)
            getPendingIntent(NOTIFICATION_ID, PendingIntent.FLAG_UPDATE_CURRENT)
        }
        val notificationBuilder = NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_send)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setContentIntent(taskStackBuilder)
                .setStyle(NotificationCompat.BigTextStyle().bigText(messageBody))
                .setPriority(NotificationManager.IMPORTANCE_HIGH)
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(true)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationBuilder.setChannelId(NOTIFICATION_CHANNEL_ID)
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    private fun handlePendingIntent(action: String?, title: String?, receiver: String?): Intent {
        return when (action) {
            NOTIFICATION_ACTION.CHAT -> {
                val receiverData = UserListDomain.Query(
                    username = title.orEmpty(),
                    email = receiver
                )
                Intent(applicationContext, ChatActivity::class.java).apply {
                    putExtras(bundleOf(EXTRAS.DATA to receiverData))
                }
            }

            else -> Intent(applicationContext, MainActivity::class.java)
        }

    }

    companion object {
        private const val NOTIFICATION_ID = 1
        private const val NOTIFICATION_CHANNEL_ID = "Firebase Channel"
        private const val NOTIFICATION_CHANNEL_NAME = "Firebase Notification"
    }
}