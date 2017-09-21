package ru.nulpointer.wtfbrowser.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import ru.nulpointer.wtfbrowser.R
import ru.nulpointer.wtfbrowser.presenter.MainViewPresenter
import ru.nulpointer.wtfbrowser.ui.fragment.TabFragment

/**
 * Created by Khaustov on 21.09.17.
 */
class TabListAdapter(private val tabList: ArrayList<TabFragment>,
                     private val mainViewPresenter: MainViewPresenter)
    : RecyclerView.Adapter<TabListAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tab = tabList[position]
        holder.header.text = tab.tabInfo.header
        holder.header.setOnClickListener {
            mainViewPresenter.onTabSelected(position)
        }
        holder.closeButton.setOnClickListener {
            mainViewPresenter.onTabClosed(position)
        }
    }

    override fun getItemCount(): Int {
        return tabList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_tab_list_main_view,
                parent, false))
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val header: TextView = view.findViewById(R.id.tab_item_header)
        val closeButton: ImageButton = view.findViewById(R.id.tab_item_close)
    }
}