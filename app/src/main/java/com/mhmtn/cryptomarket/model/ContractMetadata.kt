package com.mhmtn.cryptomarket.model

data class ContractMetadata(
    val contractDeployer: String,
    val deployedBlockNumber: Int,
    val name: String,
    val openSea: OpenSea,
    val symbol: String,
    val tokenType: String,
    val totalSupply: String
)