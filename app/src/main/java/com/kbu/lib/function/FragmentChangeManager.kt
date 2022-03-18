package com.kbu.lib.function

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.kbu.lib.R

class FragmentChangeManager {

    private fun setFragment(fragment : Fragment, fragmentManager : FragmentManager) {
        fragmentManager.beginTransaction().replace(R.id.container, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).commit()

    }

    fun setDataAtFragment(fragment: Fragment, fragmentManager: FragmentManager, title:String) {
        val bundle = Bundle()
        bundle.putString("title", title)
        fragment.arguments = bundle

        setFragment(fragment, fragmentManager)
    }

}