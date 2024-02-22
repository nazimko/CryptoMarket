package com.mhmtn.cryptomarket.repo

import com.mhmtn.cryptomarket.model.Crypto
import com.mhmtn.cryptomarket.model.CryptoList
import com.mhmtn.cryptomarket.service.CryptoAPI
import com.mhmtn.cryptomarket.util.Constants.CALL_CURRENCY
import com.mhmtn.cryptomarket.util.Response
import dagger.hilt.android.scopes.ActivityScoped
import java.lang.Exception
import javax.inject.Inject

@ActivityScoped
class CryptoRepository @Inject constructor(private val api : CryptoAPI) {

    suspend fun getCryptoList () : Response<CryptoList> {

        val response = try {

            api.getCryptoList()

        } catch (e: Exception){

            return Response.Error("Error.")
        }

        return Response.Success(response)
    }

    suspend fun getCrypto (id : String) : Response<Crypto> {

        val response = try {

            api.getCrypto(CALL_CURRENCY,id)

        }catch (e : Exception){

            return Response.Error("Error.")
        }

        return Response.Success(response)
    }
}