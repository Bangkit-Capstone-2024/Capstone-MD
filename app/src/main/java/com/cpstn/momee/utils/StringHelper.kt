package com.cpstn.momee.utils

import android.text.TextUtils
import android.util.Base64
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.Locale


object StringHelper {

    fun convertToCurrencyFormat(price: String, shouldAddCurrency: Boolean): String {
        val locale = Locale("in", "id")
        val symbols = DecimalFormatSymbols.getInstance(locale)
        symbols.groupingSeparator = '.'
        symbols.monetaryDecimalSeparator = ','

        if (shouldAddCurrency) {
            symbols.currencySymbol = symbols.currencySymbol
        } else {
            symbols.currencySymbol = ""
        }

        val df = DecimalFormat.getCurrencyInstance(locale) as DecimalFormat
        val kursIndonesia = DecimalFormat(df.toPattern(), symbols)
        kursIndonesia.maximumFractionDigits = 0

        return if (TextUtils.isEmpty(price)) {
            kursIndonesia.format(0)
        } else {
            kursIndonesia.format(java.lang.Double.parseDouble(price))
        }
    }

    fun String.encode(): String {
        return Base64.encodeToString(this.toByteArray(charset("UTF-8")), Base64.NO_WRAP)
    }

    fun convertDateToString(source: String): String {
        return try {
            val sourceFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val destFormat = SimpleDateFormat("HH.mm", Locale.getDefault())
            val convertedDate = sourceFormat.parse(source)
            return destFormat.format(convertedDate!!)
        } catch (e: Exception) {
            Constant.EMPTY_STRING
        }
    }
}