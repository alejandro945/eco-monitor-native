package com.example.ecomonitor.data.storage

import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.Filter
import kotlinx.coroutines.tasks.await

class FirebaseStorage<T : Any>(private val collectionName: String) : IStorage<T> {
    private val firestore = Firebase.firestore

    override suspend fun save(key: String, value: T) {
        firestore.collection(collectionName).document(key).set(value).await()
    }

    override suspend fun get(key: String): DocumentSnapshot {
        return firestore.collection(collectionName).document(key).get().await()
    }

    override suspend fun remove(key: String) {
        firestore.collection(collectionName).document(key).delete().await()
    }

    override suspend fun update(key: String, value: T) {
        firestore.collection(collectionName).document(key).set(value, SetOptions.merge()).await()
    }

    override suspend fun list(): QuerySnapshot {
        return firestore.collection(collectionName).get().await()
    }

    override suspend fun query(filterList: List<Filter>): QuerySnapshot {
        val collection = firestore.collection(collectionName)
        var query = collection.where(Filter())

        if (filterList.isNotEmpty()) {
            filterList.forEach { query = query.where(it) }
        }

        return query.get().await()
    }

    override fun observe(listener: (QueryDocumentSnapshot) -> Unit) {
        firestore.collection(collectionName).addSnapshotListener { snapshot, _ ->
            snapshot?.documentChanges?.forEach { change ->
                when(change.type) {
                    DocumentChange.Type.ADDED -> { listener(change.document) }
                    else -> {}
                }
            }
        }
    }
}