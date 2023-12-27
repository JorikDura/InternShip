package com.internship.di

import com.internship.data.repository.InternRepositoryImpl
import com.internship.domain.repository.InternRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindRepository(repositoryImpl: InternRepositoryImpl): InternRepository

}