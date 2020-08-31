package com.nikesh.madscalculator.data

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.nikesh.madscalculator.data.model.LoggedInUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    suspend fun login(username: String, password: String): Result<LoggedInUser> {
        val doc = getUser()
        doc.forEach { documentSnapshot ->
            documentSnapshot.data?.let {
                if (it["user"] == username && it["password"] == password) {
                    return Result.Success(LoggedInUser("admin", "Nikesh Mahajan"))
                }
            }
        }
        return Result.Error(IOException("Error logging in"))
    }

    suspend fun getUser() = Firebase.firestore.collection("usersCollection")
        .get().await().documents
}