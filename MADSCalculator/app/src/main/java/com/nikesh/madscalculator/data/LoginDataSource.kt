package com.nikesh.madscalculator.data

import com.nikesh.madscalculator.data.model.LoggedInUser
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun login(username: String, password: String): Result<LoggedInUser> {
        return try {

            val fakeUser = LoggedInUser("admin", "Nikesh Mahajan")
            // Just for demo purpose only
            if (username == "admin" && password == "admin" ) {
                Result.Success(fakeUser)
            } else {
                Result.Error(IOException("Credential not valid"))
            }
        } catch (e: Throwable) {
            Result.Error(IOException("Error logging in", e))
        }
    }
}