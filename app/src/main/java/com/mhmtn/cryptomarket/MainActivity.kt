package com.mhmtn.cryptomarket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.ads.MobileAds
import com.mhmtn.cryptomarket.ui.theme.CryptoMarketTheme
import com.mhmtn.cryptomarket.view.CryptoDetailScreen
import com.mhmtn.cryptomarket.view.CryptoFeedScreen
import com.mhmtn.cryptomarket.view.LoginScreen
import com.mhmtn.cryptomarket.view.NftDetailScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CryptoMarketTheme {

                val navController = rememberNavController()
                NavHost(navController = navController , startDestination = "login_screen") {

                    composable("login_screen"){

                        LoginScreen(navController = navController)
                    }

                    composable("crypto_feed_screen"){

                        CryptoFeedScreen(navController = navController)

                    }
                    composable("crypto_detail_screen/{cryptoId}", arguments = listOf(

                        navArgument("cryptoId") {
                            type= NavType.StringType
                        }

                    )){

                        val cryptoId = remember {
                            it.arguments?.getString("cryptoId")
                        }

                       CryptoDetailScreen(id = cryptoId ?: "",
                         navController =  navController)
                    }

                    composable("nft_detail_screen/{nftIndex}", arguments = listOf(

                        navArgument("nftIndex") {
                            type = NavType.IntType
                        }

                    )){
                        val nftIndex = remember {
                            it.arguments?.getInt("nftIndex")
                        }

                        NftDetailScreen(navController = navController,
                            nftIndex = nftIndex ?: -1)
                    }

                }
            }
        }
        MobileAds.initialize(this) {}
    }
}
