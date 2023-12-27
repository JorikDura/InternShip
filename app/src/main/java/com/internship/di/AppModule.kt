package com.internship.di

import com.internship.data.local.dao.CameraDao
import com.internship.data.local.dao.DoorDao
import com.internship.data.remote.ApiService
import com.internship.data.remote.ApiServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return ApiServiceImpl(
            client = HttpClient(Android) {
                install(Logging) {
                    level = LogLevel.ALL
                }
                install(ContentNegotiation) {
                    json()
                }
            }
        )
    }

    @Provides
    @Singleton
    fun provideRealmDataBase(): Realm {
        val config = RealmConfiguration.create(schema = setOf(CameraDao::class, DoorDao::class))
        return Realm.open(config)
    }

}