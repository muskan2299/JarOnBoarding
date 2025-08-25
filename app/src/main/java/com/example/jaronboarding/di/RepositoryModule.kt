package com.example.jaronboarding.di

import com.example.jaronboarding.data.repository.InstantSavingRepositoryImpl
import com.example.jaronboarding.domain.repository.InstantSavingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindInstantSavingRepository(
        instantSavingRepositoryImpl: InstantSavingRepositoryImpl
    ): InstantSavingRepository
}
