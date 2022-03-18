package com.kbu.lib.ui.information


import androidx.fragment.app.viewModels
import com.kbu.lib.R
import com.kbu.lib.base.BaseFragment

class InfoBooksFragment : BaseFragment(R.layout.info_books_fragment) {

    companion object {
        fun newInstance() = InfoBooksFragment()
    }

    private val viewModel: InfoBooksViewModel by viewModels()


}