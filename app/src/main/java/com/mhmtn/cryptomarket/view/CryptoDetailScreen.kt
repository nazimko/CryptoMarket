package com.mhmtn.cryptomarket.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.mhmtn.cryptomarket.model.Crypto
import com.mhmtn.cryptomarket.ui.theme.turkuaz
import com.mhmtn.cryptomarket.util.Response
import com.mhmtn.cryptomarket.viewmodel.CryptoDetailViewModel

@Composable
fun CryptoDetailScreen(
    navController: NavController,
    id: String,
    viewModel : CryptoDetailViewModel = hiltViewModel()
){

    var cryptoItem by remember {
        mutableStateOf<Response<Crypto>>(Response.Loading())
    }

    LaunchedEffect(key1 = Unit) {
        cryptoItem = viewModel.getCrypto(id)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column( verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Black)) {

            when (cryptoItem) {

                is Response.Success -> {
                    val selectedCrypto = cryptoItem.data!![0]
                    Text(
                        text = selectedCrypto.name,
                        fontSize = 30.sp,
                        modifier = Modifier.padding(2.dp),
                        fontWeight = FontWeight.Bold,
                        color = turkuaz,
                        textAlign = TextAlign.Center
                    )


                    AsyncImage(
                        model =  selectedCrypto.image,
                        contentDescription = selectedCrypto.name,
                        modifier = Modifier
                            .padding(16.dp)
                            .size(200.dp, 200.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.Gray, CircleShape)
                    )

                    Text(
                        text = "$ " + "${selectedCrypto.current_price}",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(2.dp),
                        fontWeight = FontWeight.Bold,
                        color = turkuaz,
                        textAlign = TextAlign.Center
                    )
                }

                is Response.Error -> {
                    Text(cryptoItem.message!!)
                }

                is Response.Loading -> {
                    CircularProgressIndicator(color = turkuaz)
                }

                else -> {}
            }
        }
    }

    Box(modifier = Modifier
        .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter){
        BannerAdView("ca-app-pub-3239252626734491/5040140894")
    }
}

@Composable
fun BannerAdView(adId : String) {
    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White),
        factory = { context ->
            AdView(context).apply {
                setAdSize(AdSize.BANNER)
                adUnitId = adId
                loadAd(AdRequest.Builder().build())
            }
        }
    )
}