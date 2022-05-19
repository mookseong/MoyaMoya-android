package com.kbu.lib.function

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.kbu.lib.R
import com.kbu.lib.data.recyclerData.MainViewBookList
import com.kbu.lib.data.recyclerData.SearchList

class FragmentChangeManager(private val fragment: Fragment, private val fragmentManager: FragmentManager) {

    private fun setFragment() {
        fragmentManager.beginTransaction().replace(R.id.container, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).commit()

    }

    fun setInfoFragment(title: String) {
        val bundle = Bundle()
        bundle.putString("URL", title)
        fragment.arguments = bundle
        setFragment()
    }

    fun setInfoFragment( data: MainViewBookList) {
        val bundle = Bundle()
        bundle.putString("URL", data.url)
        bundle.putString("IMG", data.img)
        fragment.arguments = bundle
        setFragment()
    }

    fun setInfoFragment( data: SearchList) {
        val bundle = Bundle()
        bundle.putString("URL", data.url)
        bundle.putString("IMG", data.img)
        bundle.putString("TITLE", data.title)
        bundle.putString("TITLE", data.text)
        fragment.arguments = bundle

        setFragment()
    }

}