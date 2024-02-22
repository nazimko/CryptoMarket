package com.mhmtn.cryptomarket.service

import com.mhmtn.cryptomarket.model.Crypto
import com.mhmtn.cryptomarket.model.CryptoList
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoAPI {

    @GET("list")
    suspend fun getCryptoList(

    ) : CryptoList

    @GET("markets")
    suspend fun getCrypto(
        @Query("vs_currency") currency : String,
        @Query("ids") id : String
    ) : Crypto

}