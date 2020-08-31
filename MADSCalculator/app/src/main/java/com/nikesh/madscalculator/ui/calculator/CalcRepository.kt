package com.nikesh.madscalculator.ui.calculator

import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class CalcRepository {

    fun storeInFirebaseFireStore(value: String) {
        CoroutineScope(IO).launch {
            val documentReference = Firebase.firestore.document("usersCollection/admin")
            var list = getHistoryListFromFireStore()
            if(list.isNullOrEmpty()){
                list = ArrayList(0)
            }
            list.add(value)
            documentReference.set(
                hashMapOf(
                    "history" to list
                ), SetOptions.merge()
            )
        }
    }

    suspend fun getHistoryListFromFireStore(): ArrayList<String>? {
        return Firebase.firestore.document("usersCollection/admin").get().await()
            .get("history") as ArrayList<String>?
    }
}