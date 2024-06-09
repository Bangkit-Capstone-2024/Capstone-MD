package com.cpstn.momee.utils

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment

inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
    Build.VERSION.SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
}

inline fun <reified T : Parcelable> Intent.parcelableArrayList(key: String): ArrayList<T>? = when {
    Build.VERSION.SDK_INT >= 33 -> getParcelableArrayListExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableArrayListExtra(key)
}

inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
    Build.VERSION.SDK_INT >= 33 -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelable(key) as? T
}

inline fun <reified T : Parcelable> Bundle.parcelableArrayList(key: String): ArrayList<T>? = when {
    Build.VERSION.SDK_INT >= 33 -> getParcelableArrayList(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableArrayList(key)
}

fun Activity.startActivityTo(to: Class<*>) {
    val intent = Intent(this, to)
    startActivity(intent)
}

fun Activity.startActivityTo(to: Class<*>, bundle: Bundle) {
    val intent = Intent(this, to).apply { putExtras(bundle) }
    startActivity(intent)
}

fun Fragment.startActivityTo(to: Class<*>) {
    val intent = Intent(requireContext(), to)
    startActivity(intent)
}

fun Fragment.startActivityTo(to: Class<*>, bundle: Bundle) {
    val intent = Intent(requireContext(), to).apply { putExtras(bundle) }
    startActivity(intent)
}