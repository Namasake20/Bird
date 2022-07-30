package com.namasake.animal.domain.use_case

import com.namasake.animal.domain.repository.AnimalRepository

class GetAnimals(
    private val repository: AnimalRepository
) {

    operator fun invoke() = repository.getBirds()
}