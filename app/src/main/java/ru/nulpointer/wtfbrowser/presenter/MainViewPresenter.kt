package ru.nulpointer.wtfbrowser.presenter

import ru.nulpointer.wtfbrowser.presenter.interfaces.IMainPresenter
import ru.nulpointer.wtfbrowser.ui.activity.IBrowserView

/**
 * Created by Khaustov on 21.09.17.
 */
class MainViewPresenter(private val browserView: IBrowserView) : IMainPresenter {
    override fun onNewTabOpened() {
        browserView.openNewTab()
    }

    override fun onTabClosed(position: Int) {
        browserView.closeTab(position)
    }

    override fun onTabSelected(position: Int) {
        browserView.switchTab(position)
    }

}