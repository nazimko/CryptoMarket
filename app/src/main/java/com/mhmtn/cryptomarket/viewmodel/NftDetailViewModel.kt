package com.mhmtn.cryptomarket.viewmodel

import androidx.lifecycle.ViewModel
import com.mhmtn.cryptomarket.model.Nft
import com.mhmtn.cryptomarket.repo.NftRepo
import com.mhmtn.cryptomarket.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NftDetailViewModel @Inject constructor(val repo : NftRepo) : ViewModel() {

    suspend fun getNft (id:Int) : Response<Nft> {
        return repo.getNft(id)
    }

}