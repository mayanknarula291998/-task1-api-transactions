@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.transactionsapp.presentation.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.transactionsapp.R
import com.example.transactionsapp.data.model.response.Transaction
import com.example.transactionsapp.presentation.viewmodel.TransactionsViewModel


@Composable
fun TransactionScreen(viewModel: TransactionsViewModel, onLogout: () -> Unit) {

    val result by viewModel.stateTransactions.collectAsState()
    val isDialogOpen = remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Scaffold(
            containerColor = Color.White,
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Transactions", color = Color.White)
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                isDialogOpen.value = true
                            }, colors = IconButtonDefaults.iconButtonColors(
                                contentColor = Color.White
                            )
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_logout),
                                contentDescription = "Logout", modifier = Modifier.size(24.dp)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.Gray,
                    )
                )
            }, content = { padding ->
                Column {
                    LogoutDialog(
                        isDialogOpen = isDialogOpen,
                        onLogoutConfirm = {
                            viewModel.clearToken()
                            isDialogOpen.value = false
                            onLogout()
                        },
                        onCancel = { isDialogOpen.value = false }
                    )
                    LazyColumn(
                        modifier = Modifier
                            .padding(padding)
                    ) {
                        items(result) { transaction ->
                            TransactionItem(transaction = transaction)
                        }
                    }
                }

            })
    }


}

@Composable
fun TransactionItem(transaction: Transaction) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 15.dp, horizontal = 15.dp)
            .background(color = Color.Gray.copy(alpha = 0.2f), shape = RoundedCornerShape(8.dp))
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_transaction),
            contentDescription = transaction.category,
            modifier = Modifier
                .size(40.dp),
            tint = Color.Black
        )
        Spacer(modifier = Modifier.padding(horizontal = 5.dp))
        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "${transaction.category}",
                    style = typography.bodyLarge.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.padding(horizontal = 10.dp))
                Text(
                    text = "${transaction.date}",
                    style = typography.bodyLarge.copy(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    ),
                    color = Color.Black
                )
            }
            Text(
                text = transaction.description.toString(),
                style = typography.bodyLarge.copy(fontSize = 14.sp, fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        }
        Spacer(modifier = Modifier.padding(horizontal = 5.dp))

        Text(
            text = "₹${transaction.amount}",
            style = typography.displayLarge.copy(fontSize = 18.sp, fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary
        )
    }
}


@Composable
fun LogoutDialog(
    isDialogOpen: MutableState<Boolean>,
    onLogoutConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    if (isDialogOpen.value) {
        Dialog(
            onDismissRequest = { isDialogOpen.value = false },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        ) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
                shadowElevation = 8.dp
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Are you sure you want to logout?",
                        style = typography.bodyLarge.copy(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(
                        modifier = Modifier.height(
                            40.dp
                        )
                    )
                    Row(
                        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.End
                    ) {
                        Button(onClick = { onCancel() }) {
                            Text(text = "No")
                        }
                        Spacer(
                            modifier = Modifier.weight(1f)
                        )
                        Button(onClick = { onLogoutConfirm() }) {
                            Text(text = "Yes")
                        }
                    }
                }
            }
        }
    }
}
