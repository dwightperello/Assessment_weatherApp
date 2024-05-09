package com.example.weatherapp_assessment.presenter.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.weatherapp_assessment.R
import com.example.weatherapp_assessment.data.local.model.User
import com.example.weatherapp_assessment.databinding.ActivityMainBinding
import com.example.weatherapp_assessment.databinding.ActivityRegisterBinding
import com.example.weatherapp_assessment.presenter.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Register : AppCompatActivity() {
    lateinit var _binding:ActivityRegisterBinding
    private val viewmodel:RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding= ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(_binding!!.root)

        _binding.buttonRegister.setOnClickListener {
            val regis= User(
                Id = 0,
                Email = _binding.editTextEmail.text.toString(),
                Name = _binding.editTextName.text.toString(),
                Password = _binding.editTextPassword.text.toString(),
                Address = _binding.editTextAddress.text.toString()
            )
            viewmodel.onEvent(events = RegisterEvents.registerEvent(regis))
            viewmodel.registerResult.observe(this, Observer {
                state-> ProcessState(state)
            })
        }

        _binding.textViewAlreadySignedUp.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }


    }

    private fun ProcessState(state: Boolean?) {
       if(state == true){
           startActivity(Intent(this, MainActivity::class.java))
           finish()
       }else
       {
           Toast.makeText(this,"Error on register",Toast.LENGTH_SHORT).show()
       }
    }
}