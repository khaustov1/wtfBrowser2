package ru.nulpointer.wtfbrowser.ui.activity

/**
 * Created by Khaustov on 21.09.17.
 */
interface IBrowserView {
    fun switchTab(position: Int)
    fun closeTab(position: Int)
    fun openNewTab()
    fun getActiveTabIndex() : Int
    fun updateAllTabs()
}