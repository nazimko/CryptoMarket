package com.mhmtn.cryptomarket.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.mhmtn.cryptomarket.MainActivity
import com.mhmtn.cryptomarket.R

private lateinit var auth: FirebaseAuth

@Composable
fun LoginScreen(navController: NavController) {

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = Color.White,
        darkIcons = true
    )

    auth = Firebase.auth
    val currentUser = auth.currentUser

    if (currentUser != null) {
        navController.navigate("crypto_feed_screen")
    }
    Column(modifier = Modifier.fillMaxSize().background(color = Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        val mail = mailLabel()
        Spacer(modifier = Modifier.height(10.dp))
        val password = passwordLabel()
        Spacer(modifier = Modifier.height(10.dp))
        Row (){
            SignUpButton(email = mail,password=password,navController=navController)
            LogInButton(email = mail,password=password,navController=navController)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun mailLabel() : String {
    var email by remember {
        mutableStateOf("")
    }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },
            placeholder = { Text(text = "Mail") },
            label = { Text(text = "Mail") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            )
        )
    }
    return email
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun passwordLabel() : String {

    var password by remember() {
        mutableStateOf("")
    }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        var passwordVisibility by remember { mutableStateOf(false) }

        val icon = if (passwordVisibility)
            painterResource(R.drawable.visibility_24)
        else
            painterResource(id = R.drawable.visibility_off_24)

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },
            placeholder = { Text(text = "Password") },
            label = { Text(text = "Password") },
            trailingIcon = {
                IconButton(onClick = {
                    passwordVisibility = !passwordVisibility
                }) {
                    Icon(
                        painter = icon,
                        contentDescription = "Visibility Icon"
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            visualTransformation = if (passwordVisibility) VisualTransformation.None
            else PasswordVisualTransformation()
        )
    }
    return password
}

@Composable
fun SignUpButton(email : String, password:String, navController: NavController) {

    val context = LocalContext.current

    Button(
        onClick = {
            if(email.isNotEmpty() && password.isNotEmpty()){
                auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {
                    Toast.makeText(context,"Kayıt Başarılı ",Toast.LENGTH_SHORT).show()
                    navController.navigate("crypto_feed_screen")
                }.addOnFailureListener{
                    Toast.makeText(context,it.localizedMessage, Toast.LENGTH_SHORT).show()
                }
            } else
            {
                Toast.makeText(context,"E-mail ve şifrenizi girdiğinizden emin olun.", Toast.LENGTH_SHORT).show()
            }
        },
        modifier = Modifier
            .padding(16.dp),
        shape = RoundedCornerShape(6.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
            containerColor = Color.Black
        )

    ) {
        Text(text = "SIGN UP", modifier = Modifier.padding(6.dp))
    }

}

@Composable
fun LogInButton(email : String, password:String, navController: NavController) {

    val context = LocalContext.current

    Button(
        onClick = {
            if(email.isNotEmpty() && password.isNotEmpty()){
                auth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
                    Toast.makeText(context,"Giriş Başarılı", Toast.LENGTH_SHORT).show()
                    navController.navigate("crypto_feed_screen")
                }.addOnFailureListener{
                    Toast.makeText(context,it.localizedMessage, Toast.LENGTH_SHORT).show()
                }
            } else
            {
                Toast.makeText(context,"E-mail ve şifrenizi girdiğinizden emin olun.", Toast.LENGTH_SHORT).show()
            }
        },
        modifier = Modifier
            .padding(16.dp),
        shape = RoundedCornerShape(6.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
            containerColor = Color.Black
        )

    ) {
        Text(text = "LOG IN", modifier = Modifier.padding(6.dp))
    }

}