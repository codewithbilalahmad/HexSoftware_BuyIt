package com.muhammad.buyit.presentation.screens.user_address

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.muhammad.buyit.presentation.components.AppTextField
import com.muhammad.domain.model.AddressDomainModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserAddressScreen(
    navHostController: NavHostController,
    viewModel: UserAddressViewModel = koinViewModel(),
    userAddress: AddressDomainModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(userAddress) {
        viewModel.onAction(UserAddressAction.GetAddressData(userAddress))
    }
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        CenterAlignedTopAppBar(
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
            title = {
                Text(text = stringResource(R.string.address_details))
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
        )
    }, bottomBar = {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                },
                contentPadding = PaddingValues(vertical = 16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.save))
            }
        }
    }) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 8.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = paddingValues
        ) {
            item("address_line") {
                AppTextField(
                    state = state.addressLine,
                    modifier = Modifier.fillMaxWidth(),
                    hint = R.string.address_line
                )
            }
            item("city") {
                AppTextField(
                    state = state.city,
                    modifier = Modifier.fillMaxWidth(),
                    hint = R.string.city
                )
            }
            item("state") {
                AppTextField(
                    state = state.state,
                    modifier = Modifier.fillMaxWidth(),
                    hint = R.string.state
                )
            }
            item("postal_code") {
                AppTextField(
                    state = state.postalCode,
                    modifier = Modifier.fillMaxWidth(),
                    hint = R.string.postal_code
                )
            }
            item("country") {
                AppTextField(
                    state = state.country,
                    modifier = Modifier.fillMaxWidth(),
                    hint = R.string.country
                )
            }
        }
    }
}