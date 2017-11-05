package ru.nulpointer.wtfbrowser.model

/**
 * Created by Khaustov on 21.09.17.
 */
class TabData(var header : String) {
    companion object {
        fun emptyTab() : TabData {
            return TabData("New tab")
        }
    }
}