package com.namasake.animal.di

import com.google.firebase.firestore.FirebaseFirestore
import com.namasake.animal.core.Constants.BIRD_COLLECTION
import com.namasake.animal.data.AnimalRepositoryImpl
import com.namasake.animal.domain.repository.AnimalRepository
import com.namasake.animal.domain.use_case.GetAnimals
import com.namasake.animal.domain.use_case.UseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideAnimalsRef(
        db: FirebaseFirestore
    ) = db.collection(BIRD_COLLECTION)

    @Provides
    fun provideAnimalRepository():AnimalRepository = AnimalRepositoryImpl()


    @Provides
    fun provideUseCases(repository: AnimalRepository)= UseCases( getAnimals = GetAnimals(repository))
}