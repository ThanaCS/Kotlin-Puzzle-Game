package com.thana.simplegame.di

import com.thana.simplegame.data.repository.sharedrepositoy.SharedRepository
import com.thana.simplegame.data.repository.sharedrepositoy.SharedRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideSharedRepository(
        sharedRepositoryImpl: SharedRepositoryImpl
    ): SharedRepository
}