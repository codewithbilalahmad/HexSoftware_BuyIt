package com.muhammad.buyit.presentation.screens.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.muhammad.buyit.R
import com.muhammad.buyit.presentation.navigation.BottomNavigationBar
import com.muhammad.buyit.presentation.navigation.Destination
import com.muhammad.buyit.presentation.screens.cart.components.CartItem
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun CartScreen(
    navHostController: NavHostController, viewModel: CartViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
        Column(modifier = Modifier.fillMaxWidth()) {
            if(state.cartList.isNotEmpty()){
                Button(
                    onClick = {
                        navHostController.navigate(Destination.CheckoutScreen)
                    },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    contentPadding = PaddingValues(vertical = 12.dp)
                ) {
                    Text(text = stringResource(R.string.checkout))
                }
            }
            BottomNavigationBar(navController = navHostController)
        }
    }, topBar = {
        CenterAlignedTopAppBar(
            title = {
                Text(text = stringResource(R.string.cart))
            },
            navigationIcon = {
                IconButton(onClick = {
                    navHostController.navigateUp()
                }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_back),
                        contentDescription = null
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
        )
    }) { paddingValues ->
        when {
            state.isCartLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    ContainedLoadingIndicator()
                }
            }

            state.cartError != null -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_error),
                        contentDescription = null, modifier = Modifier.size(80.dp)
                    )
                    Text(
                        text = state.cartError!!,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            state.cartList.isEmpty() -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(
                        16.dp,
                        Alignment.CenterVertically
                    )
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.cart_outlined),
                        contentDescription = null, modifier = Modifier.size(80.dp)
                    )
                    Text(
                        text = stringResource(R.string.no_items_found),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = paddingValues
                ) {
                    items(state.cartList, key = { it.id }) { cartItem ->
                        CartItem(cartItem = cartItem, onDeleteCartItem = { item ->
                            viewModel.onAction(CartAction.DeleteCartItem(item))
                        }, onIncrementCartQuantity = { item ->
                            viewModel.onAction(CartAction.IncrementQuantity(item))
                        }, onDecrementCartQuantity = { item ->
                            viewModel.onAction(CartAction.DecrementQuantity(item))
                        })
                    }
                }
            }
        }
    }
}