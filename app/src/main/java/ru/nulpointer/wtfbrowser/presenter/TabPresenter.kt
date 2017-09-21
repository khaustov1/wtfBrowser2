package ru.nulpointer.wtfbrowser.presenter

import ru.nulpointer.wtfbrowser.presenter.interfaces.ITabPresenter
import ru.nulpointer.wtfbrowser.ui.fragment.ITabView

/**
 * Created by Khaustov on 21.09.17.
 */
class TabPresenter(private val tabView: ITabView) : ITabPresenter {
    override fun onUrlSubmitted(url: String) {
        var formattedUrl = url
        if (!url.startsWith("http://")) {
            formattedUrl = "http://" + url
        }
        tabView.goToPage(formattedUrl)
    }

}