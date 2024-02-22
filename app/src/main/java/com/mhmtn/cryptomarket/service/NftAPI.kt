package com.mhmtn.cryptomarket.service

import com.mhmtn.cryptomarket.model.NFTList
import com.mhmtn.cryptomarket.model.Nft
import retrofit2.http.GET
import retrofit2.http.Query

interface NftAPI {

    @GET("getNFTsForCollection")
    suspend fun getNFTs (
        @Query("contractAddress") adress : String,
        @Query("withMetadata") metadata : String
    ) : NFTList

    @GET("getNFTMetadata")
    suspend fun getNFT  (
        @Query("contractAddress") adress : String,
        @Query("tokenId") id : Int,
        @Query("refreshCache") cache : String = "false"
    ) : Nft

}