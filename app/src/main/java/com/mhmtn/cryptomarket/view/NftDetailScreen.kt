package com.mhmtn.cryptomarket.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.request.ImageRequest
import com.mhmtn.cryptomarket.model.Attribute
import com.mhmtn.cryptomarket.model.Nft
import com.mhmtn.cryptomarket.util.Response
import com.mhmtn.cryptomarket.viewmodel.NftDetailViewModel

@Composable
fun NftDetailScreen(
    navController: NavController,
    nftIndex : Int,
    viewModel : NftDetailViewModel = hiltViewModel()
){
    var nft by remember {
        mutableStateOf<Response<Nft>>(Response.Loading())
    }

    LaunchedEffect(key1 = Unit) {
        nft=viewModel.getNft(nftIndex)
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.Black),
        contentAlignment = Alignment.Center
    ){
        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Black)){
            when (nft) {

                is Response.Success-> {
                    val selectedNft = nft.data!!
                    Card(Modifier.border(3.dp, Color.Gray, RectangleShape)) {
                        var expanded by remember { mutableStateOf(false) }
                        Column(verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.clickable { expanded = !expanded }) {
                            Text(
                                text = selectedNft.title,
                                fontSize = 30.sp,
                                modifier = Modifier.padding(2.dp),
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                textAlign = TextAlign.Center
                            )

                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(selectedNft.media[0].thumbnail)
                                    .decoderFactory(GifDecoder.Factory())
                                    .build(),
                                contentDescription = "Nft Icon",
                                modifier = Modifier
                                    .padding(16.dp)
                                    .size(250.dp, 250.dp)
                                    .clip(RectangleShape)
                                    .border(2.dp, Color.Gray, RectangleShape)
                            )
                            AnimatedVisibility(expanded) {
                                NftAttributesView(attributes = selectedNft.metadata.attributes)
                            }
                        }
                    }
                }
                is Response.Error -> {
                    Text(nft.message!!)
                }

                is Response.Loading -> {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }


                else -> {}
            }
        }
    }

    Box(modifier = Modifier
        .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter){
        BannerAdView("ca-app-pub-3239252626734491/8631581716")
    }

}

@Composable
fun NftAttributesView(attributes:List<Attribute>) {

    LazyColumn(contentPadding = PaddingValues(8.dp),
        modifier = Modifier.size(height = 200.dp, width =1000.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center){
        items(attributes) { Attribute->
            AttributeRow(Attribute.value,Attribute.trait_type)
        }
    }
}

@Composable
fun AttributeRow(value :String, trait_type:String) {
    Column(modifier = Modifier
        .background(color = Color.Transparent)
        .padding(6.dp)
    ) {
        Column (verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Özellik Türü : " + trait_type,
                fontSize = 20.sp,
                modifier = Modifier.padding(2.dp),
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center)

            Text(text = "Değer : " + value,
                fontSize = 20.sp,
                modifier = Modifier.padding(2.dp),
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray,
                textAlign = TextAlign.Center)
        }
    }
}

