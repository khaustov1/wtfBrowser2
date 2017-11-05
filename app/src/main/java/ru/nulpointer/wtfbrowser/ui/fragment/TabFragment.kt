package ru.nulpointer.wtfbrowser.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import android.widget.ImageButton
import ru.nulpointer.wtfbrowser.R
import ru.nulpointer.wtfbrowser.model.TabData
import ru.nulpointer.wtfbrowser.presenter.TabPresenter
import ru.nulpointer.wtfbrowser.presenter.interfaces.ITabPresenter
import ru.nulpointer.wtfbrowser.ui.activity.IBrowserView
import ru.nulpointer.wtfbrowser.utils.KeyBoardManager


/**
 * Created by Khaustov on 21.09.17.
 */
class TabFragment : Fragment(), ITabView {
    private lateinit var tabInfo: TabData
    private lateinit var browserView: IBrowserView
    private lateinit var webView: WebView
    private lateinit var goToUrlButton: ImageButton
    private lateinit var urlEditText: EditText

    private lateinit var presenter: ITabPresenter

    @SuppressWarnings("deprecation")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_tab_layout, container, false)

        presenter = TabPresenter(this)

        webView = view.findViewById(R.id.tab_item_webView)
        urlEditText = view.findViewById(R.id.tab_item_url)
        goToUrlButton = view.findViewById(R.id.tab_item_go_to_url)

        initWebView()
        initAddressSpaceUI()

        return view
    }

    private fun initWebView() {
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                view?.apply {
                    presenter.onUrlLoaded(view.title)
                }
            }
        }
        webView.webChromeClient = WebChromeClient()
        webView.settings.allowContentAccess = true
        webView.settings.allowFileAccess = true
        webView.settings.allowFileAccessFromFileURLs = true
        webView.settings.javaScriptEnabled = true
        webView.settings.setSupportZoom(true)
    }

    private fun initAddressSpaceUI() {
        urlEditText.setOnEditorActionListener { textView, id, keyEvent ->
            if (id == EditorInfo.IME_ACTION_DONE) {
                presenter.onUrlSubmitted(urlEditText.text.toString())
            }
            id == EditorInfo.IME_ACTION_DONE
        }

        goToUrlButton.setOnClickListener {
            presenter.onUrlSubmitted(urlEditText.text.toString())
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        webView.saveState(outState)
    }

    override fun goToPage(url: String) {
        KeyBoardManager.closeKeyboard(activity as AppCompatActivity)
        webView.loadUrl(url)
    }

    override fun setTitle(title: String) {
        tabInfo.header = title
        (activity as IBrowserView).updateAllTabs()
    }

    override fun getTitle(): String {
        return tabInfo.header
    }

    companion object {
        fun getInstance(tab: TabData, tabController: IBrowserView): TabFragment {
            val tabFragment = TabFragment()
            tabFragment.tabInfo = tab
            tabFragment.browserView = tabController
            return tabFragment
        }
    }
}