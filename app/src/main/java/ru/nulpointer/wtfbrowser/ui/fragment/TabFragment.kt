package ru.nulpointer.wtfbrowser.ui.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import ru.nulpointer.wtfbrowser.R
import ru.nulpointer.wtfbrowser.model.TabData
import ru.nulpointer.wtfbrowser.presenter.interfaces.ITabPresenter
import ru.nulpointer.wtfbrowser.presenter.TabPresenter
import android.view.inputmethod.InputMethodManager.HIDE_NOT_ALWAYS
import android.content.Context.INPUT_METHOD_SERVICE
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import ru.nulpointer.wtfbrowser.utils.KeyBoardManager


/**
 * Created by Khaustov on 21.09.17.
 */
class TabFragment private constructor() : Fragment(), ITabView {
    lateinit var tabInfo: TabData
    private lateinit var webView: WebView
    private lateinit var goToUrlButton: ImageButton
    private lateinit var urlEditText: EditText

    private val presenter : ITabPresenter = TabPresenter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_tab_layout, container, false)

        webView = view.findViewById<WebView>(R.id.tab_item_webView) as WebView
        webView.webViewClient = object : WebViewClient() {
            @SuppressWarnings("deprecation")
            override fun shouldOverrideUrlLoading(view :WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }
        }
        webView.webChromeClient = WebChromeClient()

        urlEditText = view.findViewById(R.id.tab_item_url)
        urlEditText.setOnEditorActionListener { textView, id, keyEvent ->
            if(id == EditorInfo.IME_ACTION_DONE) {
                presenter.onUrlSubmitted(urlEditText.text.toString())
            }
            id == EditorInfo.IME_ACTION_DONE
        }

        goToUrlButton = view.findViewById(R.id.tab_item_go_to_url)
        goToUrlButton.setOnClickListener {
            presenter.onUrlSubmitted(urlEditText.text.toString())
        }
        return view
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        webView.saveState(outState)
    }

    override fun goToPage(url: String) {
        KeyBoardManager.closeKeyboard(activity as AppCompatActivity)
        webView.loadUrl(url)
    }

    companion object {
        fun getInstance(tab: TabData): TabFragment {
            val tabFragment = TabFragment()
            tabFragment.tabInfo = tab
            return tabFragment
        }
    }
}