package com.example.weatherapp_assessment.presenter.weather.home

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.weatherapp_assessment.R
import com.example.weatherapp_assessment.data.local.model.weather
import com.example.weatherapp_assessment.data.remote.model.WeatherResponse
import com.example.weatherapp_assessment.databinding.FragmentHomeBinding
import com.example.weatherapp_assessment.presenter.main.mainViewModel
import com.example.weatherapp_assessment.presenter.weather.dashboard.DashboardViewModel
import com.example.weatherapp_assessment.presenter.weather.dashboard.localWeatherEvents
import com.example.weatherapp_assessment.util.ResultState
import com.example.weatherapp_assessment.util.TempManager
import com.example.weatherapp_assessment.util.constants
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import java.time.Instant
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val viewmodel: HomeViewModel by viewModels()
    private val weatherViewModel:DashboardViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       _binding= FragmentHomeBinding.inflate(inflater,container,false)

        viewmodel.weather.observe(viewLifecycleOwner, Observer {
                state-> ShowWeather(state)
        })
        return _binding!!.root
    }


  var saved:Boolean= false

    private fun ShowWeather(state: ResultState<WeatherResponse>?) {
        when(state){
            is ResultState.Loading -> {}
            is ResultState.Success -> {
                if(state.data!= null){
                  val weather=  state.data.weather[0]
                    _binding!!.txtcountry.text= state.data.sys.country
                    _binding!!.txtcity.text= state.data.name
                    val temp = state.data.main.temp
                    _binding!!.txttemp.text= String.format("%.1f°C", temp)
                    _binding!!.txtsunrise.text= format(state.data.sys.sunrise)
                    _binding!!.txtsunset.text= format(state.data.sys.sunset)

                    val requestOptions = RequestOptions()
                    when{
                       weather.main.contains("Clouds",ignoreCase=true)->{
                           requestOptions.placeholder(R.drawable.cloud)
                               .error(R.drawable.error)
                       }
                        weather.main.contains("Sunny",ignoreCase=true)->{
                            requestOptions.placeholder(R.drawable.sun)
                                .error(R.drawable.error)
                        }
                        weather.main.contains("rain",ignoreCase=true)->{
                            requestOptions.placeholder(R.drawable.rain)
                                .error(R.drawable.error)
                        }
                        else -> {
                            requestOptions.placeholder(R.drawable.clear)
                                .error(R.drawable.error)
                        }

                    }
                    _binding!!.txtmain.text=weather.main
                    if(isPast6(_binding!!.txtsunrise.text.toString())){
                        Glide.with(requireContext())
                            .load(R.drawable.moon)
                            .apply(requestOptions)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .into(_binding!!.ivImage)
                    }else{
                        Glide.with(requireContext())
                            .load(requestOptions)
                            .apply(requestOptions)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .into(_binding!!.ivImage)
                    }
                }else{
                    Toast.makeText(requireContext(),"No User Found", Toast.LENGTH_SHORT).show()
                }
                if(!saved) {
                    saved=true
                    val loc_weather = weather(
                        Id = 0,
                        temp = _binding?.txttemp?.text.toString(),
                        datestemp = _binding!!.txtmain.text.toString(),
                        description = _binding!!.txtcity.text.toString(),
                        sunrise = _binding!!.txtsunrise.text.toString(),
                        sunset = _binding!!.txtsunset.text.toString()
                    )
                    weatherViewModel.onEvent(localWeatherEvents.insertWeatherToDB(loc_weather))
                }
            }
            is ResultState.Error -> Toast.makeText(requireContext(),state.message ?:"An Error occured",Toast.LENGTH_LONG).show()
            else -> {Toast.makeText(requireContext(),"An Error occured",Toast.LENGTH_LONG).show()}
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    val format:(Int)->String={ it->

        val sunriseTimestamp =it
        val instant = Instant.ofEpochSecond(sunriseTimestamp.toLong())
        val dateTime = instant.atZone(ZoneId.of("UTC")).toLocalDateTime()
        val formatter = DateTimeFormatter.ofPattern("hh:mm a")
        val formattedTime = formatter.format(dateTime)
        formattedTime

    }

    @RequiresApi(Build.VERSION_CODES.O)
    val isPast6:(String)-> Boolean ={
        val formatter = DateTimeFormatter.ofPattern("hh:mm a")
        val time = LocalTime.parse(it, formatter)

        val sixPM = LocalTime.of(18, 0) // 6 PM

       time.isAfter(sixPM)
    }
}