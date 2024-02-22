package com.mhmtn.cryptomarket.view

import android.content.Context
import android.content.ContextWrapper
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CurrencyBitcoin
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.request.ImageRequest
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.mhmtn.cryptomarket.model.CryptoListItem
import com.mhmtn.cryptomarket.ui.theme.turkuaz
import com.mhmtn.cryptomarket.viewmodel.CryptoFeedViewModel
import com.mhmtn.cryptomarket.R
import com.mhmtn.cryptomarket.model.Nft

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CryptoFeedScreen(navController: NavController, viewModel: CryptoFeedViewModel = hiltViewModel()) {

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = Color.Black,
        darkIcons = false
    )

    var isChecked by rememberSaveable {
        mutableStateOf(true)
    }

    val auth = Firebase.auth

    fun Context.getActivity(): ComponentActivity? = when (this) {
        is ComponentActivity -> this
        is ContextWrapper -> baseContext.getActivity()
        else -> null
    }

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Crypto Market", modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        fontSize = 40.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        color = turkuaz
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(Color.Black),
                actions ={
                    IconButton(onClick = {
                        auth.signOut()
                        navController.popBackStack("login_screen",false)
                    }) {
                        Icon(imageVector = Icons.Default.Logout,
                            contentDescription = "Çıkış Yap",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { values ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(values)
                .background(color = Color.Black)
        ) {
            val context = LocalContext.current.getActivity()!!

            Column {
                BackHandler {
                    context.finish()
                }
                SearchBar( modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                ) {
                    viewModel.searchCryptoList(it)
                    }

                Spacer(modifier = Modifier.height(5.dp))

                Switch(
                    checked = isChecked,
                    onCheckedChange = {isChecked=it},
                    modifier = Modifier.align(Alignment.End),
                    thumbContent = if (isChecked) {
                        {
                            Icon(
                                imageVector = Icons.Filled.CurrencyBitcoin,
                                contentDescription = null,
                                modifier = Modifier.size(SwitchDefaults.IconSize),
                            )
                        }
                    } else {
                        {
                            Icon(
                                painter = painterResource(R.drawable.nft_icon),
                                contentDescription = null,
                                modifier = Modifier.size(SwitchDefaults.IconSize),
                            )
                        }
                    }
                )

                if (isChecked){
                    CryptoList(navController = navController)
                }
                else {
                    NftListe(navController=navController)
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(modifier: Modifier = Modifier,
              onSearch : (String) -> Unit = {}
) {
    var text = remember { mutableStateOf("") }

    Box (modifier = modifier) {
        TextField(value = text.value, onValueChange = {
            text.value=it
            onSearch(it)
        },  textStyle = TextStyle(color = MaterialTheme.colorScheme.tertiary),
            shape = RoundedCornerShape(12.dp),
            label = { Text(text = "Search Crypto") },
            placeholder = { Text(text = "Search..") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 6.dp)
                .background(color = Color.White, CircleShape),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Visibility Icon"
                )
            }
        )
    }
}


@Composable
fun CryptoList(navController: NavController, viewModel: CryptoFeedViewModel = hiltViewModel()) {

    val cryptoList by remember {
        viewModel.cryptoList
    }

    val errorMessage by remember {
        viewModel.errorMessage
    }

    val isLoading by remember {
        viewModel.isLoading
    }

    CryptoListView(cryptos = cryptoList, navController = navController)

    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()){

        if (isLoading){
            CircularProgressIndicator(color = turkuaz)
        }

        if (errorMessage.isNotEmpty()) {
            Text(text = "Error." )
            }
        }
    }

@Composable
fun CryptoListView(cryptos:List<CryptoListItem>,navController: NavController) {

    LazyColumn(contentPadding = PaddingValues(5.dp) ){

        items(cryptos) { crypto ->
            CryptoRow(navController = navController, crypto = crypto)
        }
    }
}

@Composable
fun CryptoRow(navController : NavController, crypto : CryptoListItem) {
    
        Column(modifier = Modifier
            .background(color = Color.Black)
            .clickable {
                navController.navigate("crypto_detail_screen/${crypto.id}")
            }) {

            Text (text = crypto.symbol,
                fontSize = 30.sp,
                modifier = Modifier.padding(2.dp),
                fontWeight = FontWeight.Bold,
                color = turkuaz
            )

            Text (text = "ID:" + crypto.id,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(2.dp),
                color = Color.White
            )

            Text (text = "Name: " + crypto.name,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(2.dp),
                color = Color.White
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
}

@Composable
fun NftListe(navController : NavController,viewModel: CryptoFeedViewModel = hiltViewModel()) {

    val nftList by remember {viewModel.nftList}
    val errorMessage_nft by remember {viewModel.errorMessage_nft}
    val isLoading_nft by remember {viewModel.isLoading_nft}

    NftListView(navController = navController,nfts = nftList)

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if(isLoading_nft){
            CircularProgressIndicator(color = turkuaz)
        }

        if (errorMessage_nft.isNotEmpty()){
            Text(text = "Error.")
        }
    }
}

@Composable
fun NftListView(navController : NavController,nfts:List<Nft>) {
    LazyColumn(contentPadding = PaddingValues(5.dp)){
        items(nfts) { nft->
            val index = nfts.indexOf(nft)
            NftRow(navController =navController,nft=nft, index = index)
        }
    }
}

@Composable
fun NftRow(navController : NavController, nft: Nft, index : Int) {

    Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically, modifier = Modifier
        .fillMaxWidth()) {
        Column(
            modifier = Modifier
                .background(color = Color.Black)
                .clickable {
                    navController.navigate("nft_detail_screen/$index")
                }
        ) {
            Text(
                text = nft.metadata.name,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(2.dp),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(nft.media[0].gateway)
                    .decoderFactory(GifDecoder.Factory())
                    .build(),
                contentDescription = nft.title,
                modifier = Modifier.size(100.dp)
            )
        }
    }
}