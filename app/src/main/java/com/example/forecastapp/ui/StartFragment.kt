package com.example.forecastapp.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.forecastapp.R
import kotlinx.android.synthetic.main.start_fragment.*

class StartFragment : Fragment(),View.OnClickListener {
    private lateinit var city: String
    private lateinit var bundle: Bundle
    companion object {
        fun newInstance() = StartFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.start_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    private fun init() {
        bundle = Bundle()
        startBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        if (v == startBtn) {
            city = cityEt.text?.trim().toString().lowercase()
            if (city.isEmpty()) {
                cityEt.error="please enter a city name"
            } else {
                bundle.putString("cityName", city)
                findNavController().navigate(R.id.action_startFragment_to_newsFragment, bundle)
            }

        }
    }

    override fun onStart() {
        super.onStart()
        cityEt.setText("")

    }



}