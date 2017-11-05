package ru.nulpointer.wtfbrowser.ui.activity

import ru.nulpointer.wtfbrowser.ui.fragment.ITabView

/**
 * Created by Khaustov on 21.09.17.
 */
interface IBrowserView {
    fun onViewCreated()
    fun switchTab(position: Int)
    fun closeTab(position: Int)
    fun openNewTab()
    fun getActiveTabIndex() : Int
    fun updateAllTabs()
    fun getTabList() : List<ITabView>
}