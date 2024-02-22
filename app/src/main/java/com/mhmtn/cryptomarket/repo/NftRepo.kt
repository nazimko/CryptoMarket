package com.mhmtn.cryptomarket.repo

import com.mhmtn.cryptomarket.model.NFTList
import com.mhmtn.cryptomarket.model.Nft
import com.mhmtn.cryptomarket.service.NftAPI
import com.mhmtn.cryptomarket.util.Constants.Adress
import com.mhmtn.cryptomarket.util.Constants.MetaData
import com.mhmtn.cryptomarket.util.Response
import dagger.hilt.android.scopes.ActivityScoped

@ActivityScoped
class NftRepo( private val api: NftAPI) {

    suspend fun getNftList() : Response<NFTList> {

        val response = try {
            api.getNFTs(adress = Adress , metadata = MetaData)
        }catch (e:Exception){
            return Response.Error("Error.")
        }
        return Response.Success(response)
    }
    suspend fun getNft(id:Int): Response<Nft> {

        val response = try {
            api.getNFT(adress = Adress, id = id)
        } catch (e: Exception) {
            return Response.Error("Error.")
        }
        return Response.Success(response)
    }

}