package com.cpstn.momee.utils

interface Mapper<in I, out O> {
    fun map(input: I): O
}

fun getLabelProduct(category: String): String {
    val listLabelMapper = mapOf(
        "0" to "baby_bed",
        "1" to "baby_car_seat",
        "2" to "baby_folding_fence",
        "3" to "bathtub",
        "4" to "booster_seats",
        "5" to "bouncer",
        "6" to "breast_pump",
        "7" to "carrier",
        "8" to "earmuffs",
        "9" to "ride_ons",
        "10" to "rocking_horse",
        "11" to "sterilizer",
        "12" to "stroller",
        "13" to "walkers"
    )
    return listLabelMapper[category].orEmpty()
}