package com.example.weatherapp_assessment.util


import com.google.common.truth.Truth.assertThat
import org.junit.Test


class registrationTest{
    @Test
    fun `empty email returns flase`(){
        val result = registration.validateRegistration(
            email = "",
            name="test",
            password = "pass1234",
            address = "test address"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `all fields completed`(){
        val result= registration.validateRegistration(
            email = "aaaa@test.com",
            name="test",
            password = "pass1234",
            address = "test address"
        )
        assertThat(result).isTrue()
    }

    @Test
    fun `email already exists returns false`() {
        val result = registration.validateRegistration(
            email = "test@test.com",
            name="test",
            password = "pass1234",
            address = "test address"
        )
        assertThat(result).isFalse()
    }
}