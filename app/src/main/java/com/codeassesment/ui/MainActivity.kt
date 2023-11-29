package com.codeassesment.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.codeassesment.databinding.ActivityMainBinding
import com.codeassesment.extn.showToast
import com.codeassesment.ui.main.adapter.UsersAdapter
import com.codeassesment.ui.main.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()

    private var binding: ActivityMainBinding? = null
    private val userAdapter by lazy {
        UsersAdapter {
            showToast("${it.name?.first} ${it.name?.last}   clicked")
        }
    }

    private val pagerAdapter by lazy { ViewPagerAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        sharedContext = this
        setRecylerViewAdapter()
        setUpViewPagerAdapter()
        setSearchListener()
        observeData()
        swipeListener()
    }

    private fun setUpViewPagerAdapter() {
        binding?.viewPager?.adapter = pagerAdapter
        binding?.viewPager?.let { viewpager ->
            binding?.tabIndicators?.let { tablayout ->
                TabLayoutMediator(tablayout, viewpager) { tab, position ->
                }.attach()
            }
        }
    }

    private fun swipeListener() {
        binding?.swipeLayout?.setOnRefreshListener {
            viewModel.refreshUserList()
        }
    }

    private fun setRecylerViewAdapter() {
        binding?.userListView?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = userAdapter
        }
    }

    private fun observeData() {
        viewModel.userList.observe(this) {
            userAdapter.updateList(it)
            pagerAdapter.updateList(it)
        }
        viewModel.uiState.observe(this) { uiState ->
            when {
                uiState.isLoading -> binding?.swipeLayout?.isRefreshing = true
                uiState.errorMessage.isNotBlank() -> showToast(uiState.errorMessage)
                else -> binding?.swipeLayout?.isRefreshing = false
            }

        }

    }


    private fun setSearchListener() {
        binding?.searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                userAdapter.filter.filter(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                userAdapter.filter.filter(newText)
                return true
            }
        })
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }


    companion object {
        @SuppressLint("StaticFieldLeak")
        var sharedContext: Context? = null
    }
}

