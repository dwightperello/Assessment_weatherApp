package com.example.weatherapp_assessment.data.local

import com.example.weatherapp_assessment.data.local.model.User
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class FakeLoginRepoTest {

    @Test
    fun testInsertUser() = runBlocking {
        // Create an instance of the repository
        val repo = fakeLoginRepo()

        // Create a user
        val user = User(
            Id = 1,
            Email = "testEmail@test.com",
            Name = "test",
            Password = "abc12345",
            Address = "test Add"
        )

        // Insert the user into the repository
        repo.insertUser(user)

        // Assert that the user was added to the repository
        assertEquals(1, repo.users.size)
        assertEquals(user, repo.users[0])
    }

    @Test
    fun testLogin()= runBlocking {
        val repo = fakeLoginRepo()
        val user = User(
            Id = 1,
            Email = "testEmail@test.com",
            Name = "test",
            Password = "abc12345",
            Address = "test Add"
        )
        repo.insertUser(user)
        val loggedInUser = repo.Login("testEmail@test.com", "abc12345")
        assertEquals(user, loggedInUser)
    }

    @Test
    fun testShouldReturnError() {
        // Create an instance of the repository
        val repo = fakeLoginRepo()

        // Set shouldReturnError to true
        repo.setshouldReturnError(true)

        // Assert that shouldReturnError is true
        assertEquals(true, repo.shouldReturnError)
    }

}