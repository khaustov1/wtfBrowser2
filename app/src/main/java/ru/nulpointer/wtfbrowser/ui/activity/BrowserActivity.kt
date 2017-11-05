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
import ru.nulpointer.wtfbrowser.ui.fragment.ITabView
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

    private var activeTabIndex: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browser_main_view_layout)

        tabListRecyclerView = findViewById(R.id.main_view_frame_tabs_list) as RecyclerView
        tabListRecyclerView.adapter = tabListAdapter
        tabListRecyclerView.layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false)

        openNewTabButton = findViewById(R.id.main_view_new_frame) as ImageButton
        openNewTabButton.setOnClickListener {
            mainViewPresenter.onNewTabOpened()
        }

        onViewCreated()
    }

    override fun onViewCreated() {
        mainViewPresenter.onViewCreated()
    }

    override fun switchTab(position: Int) {
        tabList.takeIf { tabList.isNotEmpty() }?.apply {
            supportFragmentManager.beginTransaction().hide(tabList[activeTabIndex]).commit()
            activeTabIndex = position
            supportFragmentManager.beginTransaction().show(tabList[position]).commit()
        }
    }

    override fun closeTab(position: Int) {
        tabList.takeIf { tabList.isNotEmpty() }?.apply {
            supportFragmentManager.beginTransaction()
                    .detach(tabList[position])
                    .commit()
            supportFragmentManager.beginTransaction()
                    .show(tabList[tabList.lastIndex])
                    .commit()

            tabList.removeAt(position)
            updateCurrentTabPosition()
            tabListAdapter.notifyDataSetChanged()
        }
    }

    private fun updateCurrentTabPosition() {
        tabList.takeIf { tabList.isEmpty() }?.apply { activeTabIndex = 0 } ?:
                apply { activeTabIndex = tabList.lastIndex }
    }

    override fun openNewTab() {
        val newEmptyTab = TabFragment.getInstance(TabData.emptyTab())
        tabList.add(newEmptyTab)
        supportFragmentManager.beginTransaction().hide(tabList[activeTabIndex]).commit()
        supportFragmentManager.beginTransaction().add(R.id.main_view_frame, newEmptyTab).commit()
        updateCurrentTabPosition()
        tabListAdapter.notifyDataSetChanged()
        tabListRecyclerView.smoothScrollToPosition(tabList.lastIndex)
    }

    override fun updateAllTabs() {
        tabListAdapter.notifyDataSetChanged()
    }

    override fun getActiveTabIndex(): Int = activeTabIndex

    override fun getTabList(): List<ITabView> {
        return tabList
    }

}