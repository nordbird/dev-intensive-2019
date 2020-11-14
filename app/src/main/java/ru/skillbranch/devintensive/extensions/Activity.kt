package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Activity.hideKeyboard() {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    val view = this.currentFocus ?: View(this)

    imm?.hideSoftInputFromWindow(view.windowToken, 0)
    view.clearFocus();
}

