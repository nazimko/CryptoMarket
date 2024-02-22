package com.mhmtn.cryptomarket.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhmtn.cryptomarket.model.CryptoListItem
import com.mhmtn.cryptomarket.model.Nft
import com.mhmtn.cryptomarket.repo.CryptoRepository
import com.mhmtn.cryptomarket.repo.NftRepo
import com.mhmtn.cryptomarket.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoFeedViewModel @Inject constructor(
    private val repo : CryptoRepository,
    private val repo_nft: NftRepo
) : ViewModel() {

    var cryptoList = mutableStateOf<List<CryptoListItem>>(listOf())
    var errorMessage = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    var nftList = mutableStateOf<List<Nft>>(listOf())
    var errorMessage_nft = mutableStateOf("")
    var isLoading_nft = mutableStateOf(false)

    private var asılListe = listOf<CryptoListItem>()
    private var isSearchStarting = true

    init {
        loadCryptos()
        loadNfts()
    }

    fun loadCryptos () {

        viewModelScope.launch {
            isLoading.value = true
            val list = repo.getCryptoList()

            when(list){
                is Response.Success -> {
                    val cryptos = list.data!!.mapIndexed { index, cryptoListItem ->
                        CryptoListItem(cryptoListItem.id,cryptoListItem.symbol,cryptoListItem.name)

                    }
                    isLoading.value = false
                    cryptoList.value += cryptos
                    errorMessage.value = ""
                }

                is Response.Error -> {
                    errorMessage.value = list.message!!
                    isLoading.value = false
                }

                is Response.Loading -> {
                    errorMessage.value = ""
                    isLoading.value = true
                }

                else -> {}
            }
        }
    }

    fun searchCryptoList(query : String) {
        val listToSearch = if(isSearchStarting){
            cryptoList.value
        }else{
            asılListe
        }

        viewModelScope.launch(Dispatchers.Default){

            if (query.isEmpty()){
                cryptoList.value=asılListe
                isSearchStarting=true
                return@launch
            }

            val results = listToSearch.filter {
                it.id.contains(query.trim(),ignoreCase = true)
            }

            if(isSearchStarting){
                asılListe = cryptoList.value
                isSearchStarting=false
            }
            cryptoList.value=results
        }
    }

    fun loadNfts (){
        viewModelScope.launch {
            isLoading_nft.value = true
            val result = repo_nft.getNftList()
            when(result) {
                is Response.Success -> {
                    val nftItems = result.data!!.nfts.mapIndexed { index, nft ->
                        Nft(nft.contract,nft.contractMetadata,nft.description,nft.id,nft.media,nft.metadata,nft.timeLastUpdated,nft.title,nft.tokenUri)
                    }
                    errorMessage.value = ""
                    isLoading_nft.value = false
                    nftList.value += nftItems
                }
                is Response.Error -> {
                    errorMessage_nft.value = result.message!!
                    isLoading_nft.value=false
                }

                is Response.Loading -> {
                    isLoading_nft.value = true
                    errorMessage_nft.value = ""
                }

                else -> {}
            }

        }
    }

}


