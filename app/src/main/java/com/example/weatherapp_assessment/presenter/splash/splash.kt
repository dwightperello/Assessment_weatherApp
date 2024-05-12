package com.example.weatherapp_assessment.presenter.splash

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.weatherapp_assessment.BuildConfig
import com.example.weatherapp_assessment.BuildConfig.API_KEY
import com.example.weatherapp_assessment.R
import com.example.weatherapp_assessment.databinding.ActivitySplashBinding
import com.example.weatherapp_assessment.databinding.FragmentHomeBinding
import com.example.weatherapp_assessment.presenter.main.MainActivity
import com.example.weatherapp_assessment.util.TempManager
import com.example.weatherapp_assessment.util.constants
import com.example.weatherapp_assessment.util.ecryptSharedPref
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class splash : AppCompatActivity() {
    private var _binding:ActivitySplashBinding?= null

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var apiKeyManager: ecryptSharedPref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding= ActivitySplashBinding.inflate(layoutInflater)
        setContentView(_binding!!.root)
        apiKeyManager = ecryptSharedPref(this)
        saveApiKeyIfNotExists()
       constants.API_KEY = apiKeyManager.getApiKey().toString()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request the permission if not granted
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                PERMISSION_REQUEST_CODE
            )
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                // Got last known location. In some rare situations this can be null.
                location?.let {
                    val latitude = location.latitude
                    val longitude = location.longitude

                    TempManager.latitude= latitude
                    TempManager.long= longitude
                    if(TempManager.latitude!=null && TempManager.long!=null){
                        Log.d("loc",latitude.toString())
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }else{
                        Toast.makeText(this,"Could not get Coordinates",Toast.LENGTH_SHORT).show()
                    }
                }
            }


    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {

            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {

                    return
                }
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location: Location? ->

                        location?.let {
                            val latitude = location.latitude
                            val longitude = location.longitude
                            TempManager.latitude= latitude
                            TempManager.long= longitude
                            if(TempManager.latitude!=null && TempManager.long!=null){
                                Log.d("loc",latitude.toString())
                                startActivity(Intent(this, MainActivity::class.java))
                                finish()
                            }else{
                                Toast.makeText(this,"Could not get Coordinates",Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
            } else {

            }
        }
    }
    companion object {
        private const val PERMISSION_REQUEST_CODE = 123
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun saveApiKeyIfNotExists() {
        val apiKey = apiKeyManager.getApiKey()
        if (apiKey == null) {
            if(API_KEY.isNullOrEmpty()){
                Toast.makeText(this,"no key",Toast.LENGTH_LONG).show()
            }else{
                apiKeyManager.saveApiKey(API_KEY)
            }
        }
    }
}


