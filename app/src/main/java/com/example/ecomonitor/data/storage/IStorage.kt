package com.example.ecomonitor.data.storage

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

interface IStorage<T> {
    suspend fun save(key: String, value: T)
    suspend fun get(key: String): DocumentSnapshot?
    suspend fun remove(key: String)
    suspend fun update(key: String, value: T)
    suspend fun list(): QuerySnapshot?
}