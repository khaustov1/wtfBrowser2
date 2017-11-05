package ru.nulpointer.wtfbrowser.presenter

import ru.nulpointer.wtfbrowser.presenter.interfaces.IMainPresenter
import ru.nulpointer.wtfbrowser.ui.activity.IBrowserView

/**
 * Created by Khaustov on 21.09.17.
 */
class MainViewPresenter(private val browserView: IBrowserView) : IMainPresenter {
    override fun onViewCreated() {
        browserView.getTabList().takeIf { browserView.getTabList().isEmpty() }?.apply { onNewTabOpened() }
                ?: apply { onTabSelected(browserView.getTabList().lastIndex) }
    }

    override fun onNewTabOpened() {
        browserView.openNewTab()
        browserView.switchTab(browserView.getActiveTabIndex())
    }

    override fun onTabClosed(position: Int) {
        browserView.closeTab(position)
        browserView.switchTab(browserView.getActiveTabIndex())
    }

    override fun onTabSelected(position: Int) {
        browserView.switchTab(position)
    }

}