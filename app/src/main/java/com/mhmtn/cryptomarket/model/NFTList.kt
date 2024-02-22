package com.mhmtn.cryptomarket.model

data class NFTList(
    val nextToken: String,
    val nfts: List<Nft>
)