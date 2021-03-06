package com.nikesh.madscalculator.data

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.nikesh.madscalculator.data.model.LoggedInUser
import kotlinx.coroutines.tasks.await
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    suspend fun login(username: String, password: String): Result<LoggedInUser> {
        val doc = getUser()
        if (doc == null || !doc.exists()) {
            return Result.Error(IOException("Error logging in"))
        }
        if (doc["password"] == password) {
            return Result.Success(LoggedInUser("admin", "Nikesh Mahajan"))
        }
        return Result.Error(IOException("Error logging in"))
    }

    private suspend fun getUser(): DocumentSnapshot? =
        Firebase.firestore.document("usersCollection/admin").get().await()
}