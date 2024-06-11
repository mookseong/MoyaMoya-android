package com.kbu.lib.ui.information


import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kbu.lib.R
import com.kbu.lib.base.BaseFragment
import com.kbu.lib.data.layoutData.InfoPages
import com.kbu.lib.databinding.InfoBooksFragmentBinding
import com.kbu.lib.function.InfoFunction

class InfoBooksFragment : BaseFragment<InfoBooksFragmentBinding>(R.layout.info_books_fragment) {
    private val infoBooksRecycler = InfoBooksRecycler(arrayListOf())
    private var url: String = ""
    private val infoFunction = InfoFunction()

    override fun initView() {
        super.initView()
        url = arguments?.getString("URL").toString()
        if (arguments?.getString("Title") != "" && arguments?.getString("Title") != null)
            binding.infoPages = InfoPages(
                arguments?.getString("Title").toString(),
                arguments?.getString("TitleText").toString()
            )
        else
            infoFunction.bookTitle(url, binding)

    }

    override fun afterViewCreated() {
        super.afterViewCreated()
        context?.let { Glide.with(it).load(arguments?.getString("IMG").toString()).into(binding.infoBookImg) }

        infoFunction.bookInfo(url, binding)
        infoFunction.borrowList(url, infoBooksRecycler)

        binding.infoRecyclerview.adapter = infoBooksRecycler
        binding.infoRecyclerview.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.infoRecyclerview.setHasFixedSize(true)
        binding.infoRecyclerview.addItemDecoration(DividerItemDecoration(context, 1))
    }

}