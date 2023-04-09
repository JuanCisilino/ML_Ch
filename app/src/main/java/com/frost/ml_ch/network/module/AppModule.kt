package com.frost.ml_ch.network.module

import com.frost.ml_ch.network.uc.ItemUC
import com.frost.ml_ch.network.service.MeliApi
import com.frost.ml_ch.network.repository.MeliRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMeLiInstance() = Retrofit.Builder()
        .baseUrl("https://api.mercadolibre.com/sites/MLA/")
        .client(OkHttpClient.Builder().build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MeliApi::class.java)

    @Provides
    @Singleton
    fun provideMeliRepo(api: MeliApi) = MeliRepository(api)

    @Provides
    @Singleton
    fun provideItemUC(repo: MeliRepository) = ItemUC(repo)
}