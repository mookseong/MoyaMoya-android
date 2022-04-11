package com.kbu.lib.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kbu.lib.function.DataManager
import com.kbu.lib.function.DataParser
import com.kbu.lib.function.FragmentChangeManager

abstract class BaseFragment(private val LayoutID: Int) : Fragment() {

    val libURL = "http://lib.bible.ac.kr"
    val fragChangeManager = FragmentChangeManager()
    val dataParser = DataParser()
    val dataManager = DataManager()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(LayoutID, container, false)



}
