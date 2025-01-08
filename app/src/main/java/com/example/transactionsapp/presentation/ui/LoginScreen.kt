@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.transactionsapp.presentation.ui

import android.annotation.SuppressLint
import android.content.Context
import android.widget.ImageButton
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import com.example.transactionsapp.R
import com.example.transactionsapp.data.model.request.LoginRequest
import com.example.transactionsapp.presentation.viewmodel.AuthViewModel
import com.example.transactionsapp.ui.theme.TransactionsAppTheme
import com.example.transactionsapp.utils.BiometricAuthenticator
import kotlinx.coroutines.delay


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(
    authViewModel: AuthViewModel,
    onLoginSuccess: () -> Unit,
    context: Context,
    biometric: BiometricAuthenticator
) {


    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var passwordVisible by remember { mutableStateOf(false) }

    val result by authViewModel.stateLoginResponse.collectAsState()
    val errorMsg by authViewModel.errorMsg.collectAsState()

    LaunchedEffect(result) {
        print("Data Token -${result.token}")
        if (result.token?.isNotEmpty() == true) {
            onLoginSuccess()
        }
    }

    LaunchedEffect(errorMsg) {
        if (errorMsg.isNotEmpty()) {
            Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show()
            authViewModel.clearMsg()
        }
    }

    TransactionsAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White
        ) {
            val activity = LocalContext.current as FragmentActivity
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(text = "Login Screen", color = Color.White)
                        },
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = Color.Gray,
                        )
                    )
                }, containerColor = Color.White, content = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White)
                            .padding(16.dp)
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            OutlinedTextField(
                                value = username,
                                onValueChange = { username = it },
                                label = { Text("Username") },
                                shape = RoundedCornerShape(18.dp),
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = "Phone Icon",
                                    )
                                },
                                modifier = Modifier.fillMaxWidth(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = Color.Black,
                                    focusedLabelColor = Color.Black,
                                    unfocusedLabelColor = Color.Gray,
                                    errorTextColor = Color.Red,
                                    focusedLeadingIconColor = Color.Black,
                                    unfocusedLeadingIconColor = Color.Gray,
                                    cursorColor = Color.Black,
                                    focusedBorderColor = Color.Black,
                                    unfocusedBorderColor = Color.Gray,
                                )
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            OutlinedTextField(
                                value = password,
                                onValueChange = { password = it },
                                label = { Text("Password") },
                                shape = RoundedCornerShape(18.dp),
                                singleLine = true,
                                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Lock,
                                        contentDescription = "Lock Icon",
                                    )
                                },
                                trailingIcon = {
                                    val image =
                                        if (passwordVisible) R.drawable.ic_show_pass else R.drawable.ic_hide_pass
                                    val description =
                                        if (passwordVisible) "Hide password" else "Show password"

                                    IconButton(
                                        onClick = { passwordVisible = !passwordVisible },
                                    ) {
                                        Icon(
                                            painter = painterResource(id = image),
                                            modifier = Modifier.size(18.dp),
                                            contentDescription = description,
                                        )
                                    }
                                },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Password,
                                    imeAction = ImeAction.Done
                                ),
                                modifier = Modifier.fillMaxWidth(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = Color.Black,
                                    focusedLabelColor = Color.Black,
                                    unfocusedLabelColor = Color.Gray,
                                    errorTextColor = Color.Red,
                                    focusedLeadingIconColor = Color.Black,
                                    unfocusedLeadingIconColor = Color.Gray,
                                    focusedTrailingIconColor = Color.Black,
                                    unfocusedTrailingIconColor = Color.Gray,
                                    cursorColor = Color.Black,
                                    focusedBorderColor = Color.Black,
                                    unfocusedBorderColor = Color.Gray,
                                ),
                            )

                            Spacer(modifier = Modifier.height(32.dp))

                            Button(
                                onClick = {

                                    biometric.promptBiometricAuth(
                                        title = "Login",
                                        subTitle = "Use your fingerprint",
                                        negativeButtonText = "Cancel",
                                        fragmentActivity = activity,
                                        onSuccess = {
                                            var message = "Success"
                                        },
                                        onError = { _, errorString ->
                                            var message = errorString.toString()
                                        },
                                        onFailed = {
                                            var message = "Verification error"
                                        }
                                    )

//                                    authViewModel.login(
//                                        LoginRequest(
//                                            username = username,
//                                            password = password
//                                        )
//                                    )
//                                    println("${result.token}")
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Gray,
                                    contentColor = Color.White
                                )
                            ) {
                                Text(
                                    "Login",
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        }
                    }
                }
            )

        }
    }
}
