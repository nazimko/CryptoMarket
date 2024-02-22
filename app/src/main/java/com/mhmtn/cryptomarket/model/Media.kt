package com.mhmtn.cryptomarket.model

data class Media(
    val bytes: Int,
    val format: String,
    val gateway: String,
    val raw: String,
    val thumbnail: String
)