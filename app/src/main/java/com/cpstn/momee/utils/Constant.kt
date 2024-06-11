package com.cpstn.momee.utils

import androidx.datastore.preferences.core.stringPreferencesKey


object API {
    const val AUTHORIZATION = "Authorization"
    const val MESSAGE = "message"

    const val IMAGE = "image"
}

object Firebase {
    const val DB_FIREBASE_URL = "https://momee-e9f6b-default-rtdb.asia-southeast1.firebasedatabase.app/"
    const val FCM_URL = "https://fcm.googleapis.com/v1/projects/momee-e9f6b/messages:send"
    const val SERVICE_URL = "{\n" +
            "  \"type\": \"service_account\",\n" +
            "  \"project_id\": \"momee-e9f6b\",\n" +
            "  \"private_key_id\": \"b8804b047e19f2119001c4c452350d1948fd4d34\",\n" +
            "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCTKMso+hqw//I4\\nluJTy6yRy+nHf36ObdCK1jEn0MagS6tWWn2ysaEqXlhUTNAuuTtq2DsN7orPF0yF\\nOYL4078nOEDWwNA2iiAdBBc0nPlRQBl/1UpJXIugN1ZQwVXL+7daL8KPabVWzBdy\\neoa8nmHH8i8d2KhDUrLEUO4yqoUuNOJ16kVTVx/ijPphOHrUAAGDfaxjT4gLlJCf\\np8vspERJYuqRRdV6jxhoJk44sxm0WENqMKGKKJ4Lde6FZ2LPdIR7nDdYJR6COO3Q\\nhfDg4cw8rnxDrKfKFjPpw/CJXBmS0QXqnxqgj7e6RtRNeKm3DUI00hlmVcjjA5m9\\n4EB8qunLAgMBAAECggEAEfir4tu0UcRYDOAkUK5xaza3XJUt2+/+RU5GH4IoF6BW\\nEswnSKDXuUhfSiA9Vk9pubEheSCWFBdY0XZGD+QwFnaQuIlAhECXDmQW/2ZiLjGB\\nS7K9CTuDk7PeVytdMnWRnTJy9LeonSp8fI8JEFTZRSVG6sKwPdSGH2JwzIA1loBQ\\ndQm8lpDklr35Vp3m5UKEGC5B3g/gp/56ghyrenK/q4MWFkClx9UQrtRiMY4D7IHA\\na/ahNu50XrirG3ZrQwMfaqTO4P+eQ7/0bOyWn1Luxq3WTOIJQ3AOprfzsEePCAvy\\nNJ7brrFXNUMJUHi+YUZdR6vOnGJOR5BH7WGTDaSXGQKBgQDDr+0oXyb9IJOn4/dB\\nWyyoykVG1MKc6kGdoMhqox+wtR+fPCrcwGmAVHWHgjA0pEfezMOWrKzZ4/qdhYzv\\nOazG7YTofPsXS9J4T1YcphJGrSScko4KQEklHKOg8asOAu7ip0KoLL3Yrakadhxq\\nfVVwXd7qy6tsDTy2aLAQ32Q86QKBgQDAg+5xh/rfxlJuwxKSkFaOzjlOiF6MO1cf\\nkLDq3wd5tcrAeGkQejY11dPVB1VQvjCzgGWhygfSxCXfC4saYNja8RCGudGgXZ/B\\nnoR+lwpfbgeUV4ACEjYIdY+sVBz87NHE0oMwPWD+76EEn9CVtwwiDzXlVYfH0QJR\\nfMVWvgZwkwKBgQDAKfHFNHOQHT6Cnz2+Y29erYsMPICEIRKgXTSSa3ACUkskTqx0\\nrUqtJ8Nmd7Uo7K5bH09ullEDg3om1/AHN+bwTmy29YyJWv7rAFW6uma2Gu8aBl0P\\nugNaO15//PISR1UZJFEz3wPrVNX700KiXEPbnYL7pVfIOtOE+Rb06rp/iQKBgGxu\\novHdRkeeG7X5N8/Hbol2zD5YfvB7R+ImjA9uWXaLF0z7RSobu8bsziiRvdMBnMqt\\nyhCri2aCRlZv2QSdgecnmkhYByNNOHtEvpnSluwTPD+oXRhNi5OOK8NDiSRIewpi\\nC7QzDhvpeobxQPnipPnWGWQ3nYfFSIn/PeNSmqvzAoGBAK3XQWUrndeXKle0eZeX\\n0lh5oErsGjC73elH7+ZYXQGPSao+t4U8F3KQ+t4rz/+1nkr3mgKgi7DRN4/hV46K\\nDYcJIB2oE3oufJKEWOy8rWMQQ7giNfGM9DoRsePQ0O2v/f62yo3IBOpEpRjv4orB\\n158tuj4nPak4Ic13YKtT895d\\n-----END PRIVATE KEY-----\\n\",\n" +
            "  \"client_email\": \"firebase-adminsdk-f7vwx@momee-e9f6b.iam.gserviceaccount.com\",\n" +
            "  \"client_id\": \"106677897284114970066\",\n" +
            "  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n" +
            "  \"token_uri\": \"https://oauth2.googleapis.com/token\",\n" +
            "  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n" +
            "  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-f7vwx%40momee-e9f6b.iam.gserviceaccount.com\",\n" +
            "  \"universe_domain\": \"googleapis.com\"\n" +
            "}\n"
}

object EXTRAS {
    const val DATA = "data"
}

object NOTIFICATION_ACTION {
    const val CHAT = "chat"
}

object Constant {
    const val EMPTY_STRING = ""
    const val SPACE = " "
    const val ZERO_DOUBLE = 0.0
    const val ZERO = 0
}

object Preference {
    val USER_TOKEN = stringPreferencesKey("user_token")
    val USER_NAME = stringPreferencesKey("user_name")
    val USER_EMAIL = stringPreferencesKey("user_email")
}