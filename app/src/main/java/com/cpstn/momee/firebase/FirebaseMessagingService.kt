package com.cpstn.momee.firebase

import com.google.firebase.messaging.FirebaseMessagingService

class FirebaseMessagingService: FirebaseMessagingService() {
    override fun onNewToken(token: String) {

        super.onNewToken(token)
    }
}