package ru.nulpointer.wtfbrowser.utils

import android.content.Context
import android.view.inputmethod.InputMethodManager.HIDE_NOT_ALWAYS
import android.content.Context.INPUT_METHOD_SERVICE
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager


/**
 * Created by Khaustov on 21.09.17.
 */
class KeyBoardManager {
    companion object {
        fun closeKeyboard(activity: AppCompatActivity) {
            val inputManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(activity.currentFocus.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)

        }

        fun openKeyboard(activity: AppCompatActivity) {
            val inputManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)
        }
    }
}