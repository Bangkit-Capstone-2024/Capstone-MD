package com.cpstn.momee.utils

interface Mapper<in I, out O> {
    fun map(input: I): O
}