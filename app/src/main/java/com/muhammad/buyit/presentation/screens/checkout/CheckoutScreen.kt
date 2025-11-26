package com.muhammad.buyit.presentation.screens.checkout

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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.muhammad.buyit.R
import com.muhammad.buyit.presentation.navigation.Destination
import com.muhammad.buyit.presentation.screens.checkout.components.ChoosePaymentMethodSection
import com.muhammad.buyit.presentation.screens.checkout.components.OrderAddressSection
import com.muhammad.buyit.presentation.screens.checkout.components.OrderSummarySection
import com.muhammad.data.model.toAddressDataModel
import com.muhammad.domain.utils.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun CheckoutScreen(
    navHostController: NavHostController,
    viewModel: CheckoutViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    ObserveAsEvents(viewModel.events) {event ->
        when(event){
            CheckoutEvents.OnPlaceOrderSuccess -> {
                navHostController.navigate(Destination.OrderSuccessScreen)
            }
        }
    }
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        CenterAlignedTopAppBar(
            title = {
                Text(text = stringResource(R.string.checkout))
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
    }, bottomBar = {
        ChoosePaymentMethodSection(onPlaceOrder = {
            viewModel.onAction(CheckoutAction.PlaceOrder(state.userAddress))
        })
    }) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = paddingValues
        ) {
            item("address_section") {
                OrderAddressSection(location = state.userAddress, onClick = {
                    val address = state.userAddress.toAddressDataModel()
                    navHostController.navigate(Destination.UserAddressScreen(address))
                })
            }
            item("order_summary_section") {
                when {
                    state.isCartSummaryLoading -> {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            ContainedLoadingIndicator()
                        }
                    }

                    state.cartSummaryError != null -> {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.ic_error),
                                contentDescription = null, modifier = Modifier.size(80.dp)
                            )
                            Text(
                                text = state.cartSummaryError!!,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }

                    state.cartSummary != null -> {
                        OrderSummarySection(orderSummary = state.cartSummary!!)
                    }
                }
            }
        }
    }
}