package com.mhmtn.cryptomarket.di

import com.mhmtn.cryptomarket.repo.CryptoRepository
import com.mhmtn.cryptomarket.repo.NftRepo
import com.mhmtn.cryptomarket.service.CryptoAPI
import com.mhmtn.cryptomarket.service.NftAPI
import com.mhmtn.cryptomarket.util.Constants.BASE_URL
import com.mhmtn.cryptomarket.util.Constants.BASE_URL_NFT
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideCryptoRepository(api : CryptoAPI) = CryptoRepository(api)

    @Singleton
    @Provides
    fun provideCryptoApi () : CryptoAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(CryptoAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideNftRepo (api: NftAPI) = NftRepo(api)

    @Singleton
    @Provides
    fun provideNftApi() : NftAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL_NFT)
            .build().create(NftAPI::class.java)
    }



}