package com.cpstn.momee.utils

import android.text.TextUtils
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
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
}