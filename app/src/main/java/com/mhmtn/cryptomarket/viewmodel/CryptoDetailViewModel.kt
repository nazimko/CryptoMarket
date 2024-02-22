package com.mhmtn.cryptomarket.viewmodel

import androidx.lifecycle.ViewModel
import com.mhmtn.cryptomarket.model.Crypto
import com.mhmtn.cryptomarket.repo.CryptoRepository
import com.mhmtn.cryptomarket.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CryptoDetailViewModel @Inject constructor(private val repo : CryptoRepository) : ViewModel() {

    suspend fun getCrypto (id:String) : Response<Crypto> {
        val result = repo.getCrypto(id)
        return result
    }

}