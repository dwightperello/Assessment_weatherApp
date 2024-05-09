package com.example.weatherapp_assessment.presenter.weather.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp_assessment.data.local.model.weather
import com.example.weatherapp_assessment.databinding.FragmentDashboardBinding
import com.example.weatherapp_assessment.databinding.FragmentHomeBinding
import com.example.weatherapp_assessment.presenter.weather.ui.home.HomeViewModel
import com.example.weatherapp_assessment.util.ResultState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val viewmodel: DashboardViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentDashboardBinding.inflate(inflater,container,false)

        viewmodel.savedWeather.observe(this, Observer { state->
            ProcessWeather(state)
        })

        return _binding!!.root
    }

    private fun ProcessWeather(state: ResultState<List<weather>>?) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}