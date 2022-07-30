package com.namasake.animal.domain.repository

import com.namasake.animal.domain.model.Bird
import kotlinx.coroutines.flow.Flow

interface AnimalRepository {
    fun getBirds(): Flow<List<Bird>>
}