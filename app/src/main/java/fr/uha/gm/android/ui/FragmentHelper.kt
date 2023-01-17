package fr.uha.gm.android.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

object FragmentHelper {

    fun changeTitle(fragment: Fragment, title: String) {
        val compatActivity = fragment.activity as AppCompatActivity? ?: return
        val actionBar = compatActivity.supportActionBar ?: return
        actionBar.title = title
    }

    fun invalidateOptionsMenu(fragment: Fragment) {
        val activity = fragment.activity ?: return
        activity.invalidateOptionsMenu()
    }
}