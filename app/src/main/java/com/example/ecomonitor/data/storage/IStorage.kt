package com.example.ecomonitor.data.storage

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

interface IStorage<T> {
    fun save(key: String, value: T)
    fun get(key: String): Task<DocumentSnapshot?>
    fun remove(key: String)
    fun update(key: String, value: T)
    fun list(): Task<QuerySnapshot?>
}