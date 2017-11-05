package ru.nulpointer.wtfbrowser.presenter

import ru.nulpointer.wtfbrowser.presenter.interfaces.ITabPresenter
import ru.nulpointer.wtfbrowser.ui.activity.IBrowserView
import ru.nulpointer.wtfbrowser.ui.fragment.ITabView

/**
 * Created by Khaustov on 21.09.17.
 */
const val HTTP_SCHEME: String = "http://"

class TabPresenter(private val tabView: ITabView, private val tabController: IBrowserView) : ITabPresenter {
    override fun onUrlSubmitted(url: String) {
        var formattedUrl = url
        if (!url.startsWith(HTTP_SCHEME)) {
            formattedUrl = HTTP_SCHEME + url
        }
        tabView.goToPage(formattedUrl)
    }

    override fun onUrlLoaded(pageTitle: String) {
        tabView.setTitle(pageTitle)
        tabController.updateAllTabs()
    }

}