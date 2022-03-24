package com.kbu.lib.function

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.kbu.lib.R

class FragmentChangeManager {

    private fun setFragment(fragment: Fragment, fragmentManager: FragmentManager) {
        fragmentManager.beginTransaction().replace(R.id.container, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).commit()

    }

    fun setDataAtFragment(fragment: Fragment, fragmentManager: FragmentManager, title: String) {
        val bundle = Bundle()
        bundle.putString("URL", title)
        fragment.arguments = bundle

        setFragment(fragment, fragmentManager)
    }

    fun setDataAtFragment(
        fragment: Fragment,
        fragmentManager: FragmentManager,
        URL: String,
        IMG: String
    ) {
        val bundle = Bundle()
        bundle.putString("URL", URL)
        bundle.putString("IMG", IMG)
        fragment.arguments = bundle

        setFragment(fragment, fragmentManager)
    }

    fun setDataAtFragment(
        fragment: Fragment,
        fragmentManager: FragmentManager,
        URL: String,
        IMG: String,
        TITLE: String
    ) {
        val bundle = Bundle()
        bundle.putString("URL", URL)
        bundle.putString("IMG", IMG)
        bundle.putString("TITLE", TITLE)
        fragment.arguments = bundle

        setFragment(fragment, fragmentManager)
    }

    fun setDataAtFragment(
        fragment: Fragment,
        fragmentManager: FragmentManager,
        URL: String,
        IMG: String,
        TITLE: String,
        TITLE_TEXT: String
    ) {
        val bundle = Bundle()
        bundle.putString("URL", URL)
        bundle.putString("IMG", IMG)
        bundle.putString("Title", TITLE)
        bundle.putString("TitleText", TITLE_TEXT)
        fragment.arguments = bundle

        setFragment(fragment, fragmentManager)
    }

}