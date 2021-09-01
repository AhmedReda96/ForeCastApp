package com.example.forecastapp.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.example.forecastapp.R
import com.example.forecastapp.utils.ConnectionDetector
import com.example.forecastapp.viewModel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.news_fragment.*

@AndroidEntryPoint
class NewsFragment : Fragment(), View.OnClickListener {

    private  val TAG = "NewsFragment"
    private lateinit var viewModel: NewsViewModel
    private lateinit var cityName: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.news_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }


    private fun init() {
        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)

        cityName = arguments?.getString("cityName").toString()
        cityTv.text = cityName

        newsRV.apply {
            layoutManager =
                GridLayoutManager(activity, 1, GridLayoutManager.VERTICAL, false)
            itemAnimator = DefaultItemAnimator()

        }
        backBtn.setOnClickListener(this)
        retryBtn.setOnClickListener(this)


        getNews()

    }

    private fun getNews() {

        swipe.isRefreshing = true
        viewModel.getNewsData("cairo")

        viewModel.getNewsList().observe(requireActivity(), Observer {
            Log.d(
                TAG,
                "logTag getNewsData : ${it.size}"
            )
        })

    }

    fun checkInternetConnection(): Boolean {
        var isInternetPresent: Boolean = false
        val cd: ConnectionDetector = ConnectionDetector(requireActivity())
        isInternetPresent = cd.isConnectingToInternet
        return isInternetPresent
    }

    override fun onClick(v: View?) {
        if (v == backBtn) {
            requireActivity().supportFragmentManager.popBackStack()
        }
        if (v == retryBtn) {
            getNews()
        }
    }

}