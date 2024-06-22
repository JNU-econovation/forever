package com.fourever.forever.data.di

import com.fourever.forever.data.repository.FakeFileRepositoryImpl
import com.fourever.forever.data.repository.FileRepository
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
    abstract fun bindsFileRepository(
        fileRepository: FakeFileRepositoryImpl
    ): FileRepository
}