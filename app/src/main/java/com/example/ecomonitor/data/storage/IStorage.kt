package com.example.ecomonitor.data.storage

import com.google.firebase.firestore.DocumentSnapshot

interface IStorage<T> {
    fun save(key: String, value: T)
    fun get(key: String): DocumentSnapshot?
    fun remove(key: String)
    fun update(key: String, value: T)

    fun list(): List<DocumentSnapshot>
}