package ru.nulpointer.wtfbrowser.ui.fragment


/**
 * Created by Khaustov on 21.09.17.
 */
interface ITabView {
    fun goToPage(url: String)
    fun setTitle(title: String)
    fun getTitle() : String
}