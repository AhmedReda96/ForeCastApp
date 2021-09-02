package com.example.forecastapp.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.example.forecastapp.R
import com.example.forecastapp.helper.NewsAdapter
import com.example.forecastapp.utils.ConnectionDetector
import com.example.forecastapp.viewModel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.news_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@AndroidEntryPoint
class NewsFragment : Fragment(), View.OnClickListener {

    private val TAG = "NewsFragment"
    private lateinit var viewModel: NewsViewModel
    private lateinit var cityName: String
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var connectionDetector: ConnectionDetector


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
        connectionDetector = ConnectionDetector(requireActivity())
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

        if (connectionDetector.checkInternetConnection()) {
            noConnectionLin.visibility = View.GONE
            Log.d(
                "TAG", "logTag getNews: checkInternetConnection=True"
            )
            viewModel.getNewsData(cityName)

            viewModel.MLD?.observe(requireActivity(), Observer {
                when (it) {
                    "insertIntoRoom" -> {
                        collectData()
                        warningMessageTv.visibility = View.GONE
                    }
                    "noData" -> {
                        Toast.makeText(context, "No Results", Toast.LENGTH_LONG).show()
                        swipe.isRefreshing = false
                        swipe.isEnabled = false

                    }


                }
            })


        } else {
            viewModel.cityIsExist(cityName).let {
                if (it) {
                    collectData()
                    warningMessageTv.visibility = View.VISIBLE


                } else {
                    noConnectionLin.visibility = View.VISIBLE
                    swipe.isRefreshing = false
                    swipe.isEnabled = false


                }

            }

        }
    }


    override fun onClick(v: View?) {
        if (v == backBtn) {
            requireActivity().supportFragmentManager.popBackStack()
        }
        if (v == retryBtn) {
            getNews()
        }
    }


    private fun collectData() {
        viewModel.getNewsDataFromRoom(cityName)?.observe(requireActivity(), Observer {

            if (it!!.isEmpty()) {
                Log.d("TAG", "logTag getRoomNews:isEmpty")

            } else {
                newsAdapter = NewsAdapter(it, requireActivity())
                newsAdapter.notifyDataSetChanged()
                newsRV.adapter = newsAdapter
                swipe.isRefreshing = false
                newsRV.visibility = View.VISIBLE
                swipe.isEnabled = false
            }
        })


    }

}

