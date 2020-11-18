package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.util.TypedValue

fun Activity.hideKeyboard() {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    val view = this.currentFocus ?: View(this)

    imm?.hideSoftInputFromWindow(view.windowToken, 0)
    view.clearFocus();
}

fun Activity.isKeyboardOpen(): Boolean {
    val defaultKeyboardDP = 148f

    val estimatedKeyboardHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, defaultKeyboardDP, resources.displayMetrics).toInt()
    val activityRootView = findViewById<View>(android.R.id.content)
    val rect = Rect()
    activityRootView.getWindowVisibleDisplayFrame(rect)

    val heightDiff = activityRootView.rootView.height - (rect.bottom - rect.top)
    return heightDiff >= estimatedKeyboardHeight
}

fun Activity.isKeyboardClosed(): Boolean {
    return !this.isKeyboardOpen()
}

