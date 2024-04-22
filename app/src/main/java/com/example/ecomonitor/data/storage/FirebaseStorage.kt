package com.example.ecomonitor.data.storage

import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.firestore


class FirebaseStorage<T : Any>(private val collectionName: String) : IStorage<T> {

    private val firestore = Firebase.firestore

    override fun save(key: String, value: T) {
        firestore.collection(collectionName).document(key).set(value)
    }

    override fun get(key: String): DocumentSnapshot? {
        return firestore.collection(collectionName).document(key).get().result
    }

    override fun remove(key: String) {
        firestore.collection(collectionName).document(key).delete()
    }

    override fun update(key: String, value: T) {
        firestore.collection(collectionName).document(key).set(value)
    }

    override fun list(): List<DocumentSnapshot> {
        return firestore.collection(collectionName).get().result.documents
    }
}