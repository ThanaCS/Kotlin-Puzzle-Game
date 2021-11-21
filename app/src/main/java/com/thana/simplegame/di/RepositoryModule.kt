package com.thana.simplegame.di

import com.thana.simplegame.data.repository.sharedrepositoy.SharedRepository
import com.thana.simplegame.data.repository.sharedrepositoy.SharedRepositoryImpl
import com.thana.simplegame.data.repository.soundrepository.SoundRepository
import com.thana.simplegame.data.repository.soundrepository.SoundRepositoryImpl
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

    @Binds
    abstract fun provideSoundRepository(
        soundRepositoryImpl: SoundRepositoryImpl
    ): SoundRepository
}