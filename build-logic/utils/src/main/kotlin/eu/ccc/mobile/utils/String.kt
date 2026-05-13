package eu.ccc.mobile.utils

fun String.wrap() = "\"$this\""

fun String.unwrap() = this.removeSurrounding("\"")