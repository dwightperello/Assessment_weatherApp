package com.example.weatherapp_assessment.util
import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class ecryptSharedPref(private val context: Context) {

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPrefs = EncryptedSharedPreferences.create(
        context,
        "encrypted_file",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun saveApiKey(apiKey: String) {
        val editor = sharedPrefs.edit()
        editor.putString("api_key", apiKey)
        editor.apply()
    }

    fun getApiKey(): String? {
        return sharedPrefs.getString("api_key", null)
    }
}