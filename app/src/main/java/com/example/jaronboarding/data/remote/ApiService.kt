package com.example.jaronboarding.data.remote

import com.example.jaronboarding.data.remote.dto.InstantSavingResponse
import retrofit2.http.GET

interface ApiService {
    @GET("_assets/shared/education-metadata.json")
    suspend fun getInstantSavingData(): InstantSavingResponse
}
