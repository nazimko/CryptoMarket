package com.mhmtn.cryptomarket.model

data class OpenSea(
    val bannerImageUrl: String,
    val collectionName: String,
    val collectionSlug: String,
    val description: String,
    val discordUrl: String,
    val floorPrice: Double,
    val imageUrl: String,
    val lastIngestedAt: String,
    val safelistRequestStatus: String,
    val twitterUsername: String
)