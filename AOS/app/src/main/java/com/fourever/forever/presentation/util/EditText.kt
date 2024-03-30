package com.fourever.forever.presentation.util

fun EditText(text: String, max_length: Int): String {
    return if (text.length > max_length) {
        text.substring(0 until max_length) + "..."
    } else {
        text
    }
}