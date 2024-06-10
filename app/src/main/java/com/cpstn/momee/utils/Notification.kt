package com.cpstn.momee.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import kotlin.coroutines.CoroutineContext

object Notification {

    private const val POST_URL = Firebase.FCM_URL

    fun sendChatNotification(fcmToken: String, title: String, receiver: String, body: String, dispatcher: CoroutineContext) {
        CoroutineScope(dispatcher).launch {
            val notificationObj = JSONObject().apply {
                put("title", title)
                put("body", body)
                put("receiver", receiver)
            }
            val messageObject = JSONObject().apply {
                put("token", fcmToken)
                put("data", notificationObj)
            }
            val jsonBody = JSONObject().apply { put("message", messageObject) }
            var urlConnection: HttpURLConnection? = null
            var reader: BufferedReader? = null
            val stringBuilder = StringBuilder()

            try {
                val url = URL(POST_URL)
                urlConnection = url.openConnection() as HttpURLConnection
                urlConnection.requestMethod = "POST"
                urlConnection.setRequestProperty("Content-Type", "application/json")
                urlConnection.setRequestProperty("Authorization", "Bearer ${AccessToken.getAccessToken()}")
                urlConnection.doOutput = true

                // Tulis body JSON ke output stream
                val os = urlConnection.outputStream
                val writer = BufferedWriter(OutputStreamWriter(os, "UTF-8"))
                writer.write(jsonBody.toString())
                writer.flush()
                writer.close()
                os.close()

                // Baca respon
                val inputStream = urlConnection.inputStream
                reader = BufferedReader(InputStreamReader(inputStream))

                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    stringBuilder.append(line).append("\n")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                urlConnection?.disconnect()
                try {
                    reader?.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}