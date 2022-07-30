package com.namasake.animal.data

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.namasake.animal.core.Constants.BIRD_COLLECTION
import com.namasake.animal.domain.model.Bird
import com.namasake.animal.domain.repository.AnimalRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class AnimalRepositoryImpl:AnimalRepository {
    @ExperimentalCoroutinesApi
    override fun getBirds(): Flow<List<Bird>> = callbackFlow {
        var eventsCollection: CollectionReference? = null
        try {
            eventsCollection = FirebaseFirestore.getInstance().collection(BIRD_COLLECTION)
        } catch (e: Throwable) {
            close(e)
        }

        val subscription = eventsCollection?.addSnapshotListener { snapshot, _ ->
            if (snapshot == null) {
                    return@addSnapshotListener
            }
            try {
                val allSpecimens = ArrayList<Bird>()
                val documents = snapshot.documents
                documents.forEach {
                    val specimen = it.toObject(Bird::class.java)
                    if (specimen != null) {
                        specimen.id = it.id
                        allSpecimens.add(specimen)
                        }
                    }
                    trySend(allSpecimens)

                } catch (e: Throwable) {
                    println("something wrong")
                }
        }
            awaitClose { subscription?.remove() }
    }


}