package com.example.jaronboarding.domain.repository

import com.example.jaronboarding.domain.model.InstantSavingData

interface InstantSavingRepository {
    suspend fun getInstantSavingData(): Result<InstantSavingData>
}