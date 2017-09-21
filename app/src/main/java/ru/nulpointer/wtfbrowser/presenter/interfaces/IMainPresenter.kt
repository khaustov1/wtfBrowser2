package ru.nulpointer.wtfbrowser.presenter.interfaces

/**
 * Created by Khaustov on 21.09.17.
 */
interface IMainPresenter {
    fun onNewTabOpened()
    fun onTabClosed(position: Int)
    fun onTabSelected(position: Int)
}