package com.cpstn.momee.utils

infix fun Int.percentOf(value: Int): Int {
    return if (this == 0) 0
    else ((this.toDouble() / 100) * value).toInt()
}