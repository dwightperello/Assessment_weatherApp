package com.example.weatherapp_assessment.presenter.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.weatherapp_assessment.data.local.model.User
import com.example.weatherapp_assessment.databinding.ActivityMainBinding
import com.example.weatherapp_assessment.presenter.register.Register
import com.example.weatherapp_assessment.presenter.register.RegisterViewModel
import com.example.weatherapp_assessment.presenter.weather.WeatherActivity
import com.example.weatherapp_assessment.util.ResultState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var _binding:ActivityMainBinding
    private val viewmodel: mainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding!!.root)

        _binding.buttonRegister.setOnClickListener {
            startActivity(Intent(this, Register::class.java))

        }

        _binding.buttonLogin.setOnClickListener {
            viewmodel.onEvent(events = LoginEvents.login(_binding.editTextUsername.text.toString(),_binding.editTextPassword.text.toString()))
            viewmodel.user.observe(this, Observer {
                state->ProcessUser(state)
            })
        }
    }

    private fun ProcessUser(state: ResultState<User>?) {
        when(state){
            is ResultState.Loading ->{}
            is ResultState.Success ->{
                if(state.data==null){
                    Toast.makeText(this,"No User Found",Toast.LENGTH_SHORT).show()
                }else{
                    startActivity(Intent(this, WeatherActivity::class.java))
                    finish()
                    }
                }
            is ResultState.Error -> Toast.makeText(this,state.message ?:"An Error occured",Toast.LENGTH_LONG).show()
            else -> {Toast.makeText(this,"unknown error",Toast.LENGTH_LONG).show()}
        }
    }

}