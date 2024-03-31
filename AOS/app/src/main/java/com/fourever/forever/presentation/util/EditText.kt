package com.fourever.forever.presentation.util

fun editText(text: String, maxLength: Int): String {
    return if (text.length > maxLength) {
        text.substring(0 until maxLength) + "..."
    } else {
        text
    }
}