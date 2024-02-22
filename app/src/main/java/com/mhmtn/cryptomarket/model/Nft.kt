package com.mhmtn.cryptomarket.model

data class Nft(
    val contract: Contract,
    val contractMetadata: ContractMetadata,
    val description: String,
    val id: İd,
    val media: List<Media>,
    val metadata: Metadata,
    val timeLastUpdated: String,
    val title: String,
    val tokenUri: TokenUri
)