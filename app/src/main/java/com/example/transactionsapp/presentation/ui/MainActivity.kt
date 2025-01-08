package com.example.transactionsapp.presentation.ui

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.transactionsapp.presentation.viewmodel.AuthViewModel
import com.example.transactionsapp.presentation.viewmodel.TransactionsViewModel
import com.example.transactionsapp.utils.BiometricAuthenticator
import com.example.transactionsapp.utils.LOGIN
import com.example.transactionsapp.utils.SecurePreferences
import com.example.transactionsapp.utils.TRANSACTIONS
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    private val authViewModel: AuthViewModel by viewModels()
    private val transactionsViewModel: TransactionsViewModel by viewModels()

    @Inject
    lateinit var securePreferences: SecurePreferences

    private lateinit var biometricAuthenticator: BiometricAuthenticator

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        biometricAuthenticator = BiometricAuthenticator(this)
        setContent {
            val navController = rememberNavController()
            val destination = if (securePreferences.getToken().isNullOrBlank()) {
                LOGIN
            } else {
                TRANSACTIONS
            }
            NavHost(
                navController = navController,
                startDestination = destination
            ) {
                composable(LOGIN) {
                    LoginScreen(
                        authViewModel = authViewModel,
                        context = applicationContext,
                        biometric = biometricAuthenticator,
                        onLoginSuccess = {
                            navController.navigate(TRANSACTIONS)
                        }
                    )

                }
                composable(TRANSACTIONS) {
                    callApi()
                    TransactionScreen(viewModel = transactionsViewModel, onLogout = {
                        authViewModel.clearData()
                        navController.navigate(LOGIN)
                    })
                }
            }
        }
    }


    override fun onResume() {
        super.onResume()
        val activity = this@MainActivity as FragmentActivity
        biometricAuthenticator.promptBiometricAuth(
            title = "Login",
            subTitle = "Use your fingerprint",
            negativeButtonText = "Cancel",
            fragmentActivity = activity,
            onSuccess = {
                showToast("Sucess")
            },
            onError = { _, errorString ->
                showToast(errorString.toString())
            },
            onFailed = {
                showToast("Verification error")
            }
        )
    }

    private fun callApi() {
        transactionsViewModel.fetchTransactions()
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

}




