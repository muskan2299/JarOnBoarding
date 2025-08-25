package com.example.jaronboarding.domain.usecase

import com.example.jaronboarding.domain.model.InstantSavingData
import com.example.jaronboarding.domain.repository.InstantSavingRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetInstantSavingDataUseCase @Inject constructor(
    private val repository: InstantSavingRepository
) {
    suspend operator fun invoke(): Result<InstantSavingData> {
        return repository.getInstantSavingData()
    }
}