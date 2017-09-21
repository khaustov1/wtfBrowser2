package ru.nulpointer.wtfbrowser.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageButton
import ru.nulpointer.wtfbrowser.R
import ru.nulpointer.wtfbrowser.model.TabData
import ru.nulpointer.wtfbrowser.presenter.MainViewPresenter
import ru.nulpointer.wtfbrowser.ui.adapter.TabListAdapter
import ru.nulpointer.wtfbrowser.ui.fragment.TabFragment

/**
 * Created by Khaustov on 21.09.17.
 */
class BrowserActivity : AppCompatActivity(), IBrowserView {

    private val tabList = ArrayList<TabFragment>()
    private val mainViewPresenter: MainViewPresenter = MainViewPresenter(this)
    private val tabListAdapter: TabListAdapter = TabListAdapter(tabList, mainViewPresenter)

    private lateinit var openNewTabButton: ImageButton
    private lateinit var tabListRecyclerView: RecyclerView

    private var activeTabIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browser_main_view_layout)

        tabListRecyclerView = findViewById(R.id.main_view_frame_tabs_list) as RecyclerView
        tabListRecyclerView.adapter = tabListAdapter
        tabListRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        openNewTabButton = findViewById(R.id.main_view_new_frame) as ImageButton
        openNewTabButton.setOnClickListener {
            mainViewPresenter.onNewTabOpened()
        }

        setupBrowserTabs()
    }

    private fun setupBrowserTabs() {
        when (tabList.size) {
            0 -> openNewTab()
            else -> switchTab(tabList.size - 1)
        }
    }

    override fun switchTab(position: Int) {
        supportFragmentManager.beginTransaction().hide(tabList[activeTabIndex]).commit()
        activeTabIndex = position
        supportFragmentManager.beginTransaction().show(tabList[position]).commit()
    }

    override fun closeTab(position: Int) {
        when (tabList.size) {
            0 -> {
                val emptyTab = TabFragment.getInstance(TabData.emptyTab())
                tabList.add(emptyTab)
                supportFragmentManager.beginTransaction()
                        .replace(R.id.main_view_frame, emptyTab)
                        .commit()
            }
            else -> {
                supportFragmentManager.beginTransaction()
                        .detach(tabList[position])
                        .commit()
                supportFragmentManager.beginTransaction()
                        .show(tabList[tabList.size - 1])
                        .commit()
            }
        }
        tabList.removeAt(position)
        updateCurrentTabPosition()
        tabListAdapter.notifyDataSetChanged()
    }

    private fun updateCurrentTabPosition() {
        activeTabIndex = when (tabList.size) {
            0 -> 0
            else -> tabList.size - 1
        }
    }

    override fun openNewTab() {
        val tab = TabFragment.getInstance(TabData.emptyTab())
        if (tabList.size > 0) {
            supportFragmentManager.beginTransaction().hide(tabList[activeTabIndex]).commit()
        }
        tabList.add(tab)
        supportFragmentManager.beginTransaction().add(R.id.main_view_frame, tab).commit()
        updateCurrentTabPosition()
        tabListAdapter.notifyDataSetChanged()
    }

    override fun getActiveTabIndex(): Int = activeTabIndex

}