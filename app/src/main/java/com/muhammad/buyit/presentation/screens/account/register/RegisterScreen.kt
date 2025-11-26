package com.muhammad.buyit.presentation.screens.account.register

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.muhammad.buyit.R
import com.muhammad.buyit.presentation.components.AppTextField
import com.muhammad.buyit.presentation.navigation.Destination
import com.muhammad.domain.utils.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterScreen(
    navHostController: NavHostController,
    viewModel: RegisterViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            RegisterEvent.OnRegisterSuccess -> {
                navHostController.navigate(Destination.HomeScreen) {
                    popUpTo(Destination.RegisterScreen) {
                        inclusive = true
                    }
                }
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.register),
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(Modifier.height(32.dp))
        AppTextField(value = state.username, onValueChange = { newValue ->
            viewModel.onAction(RegisterAction.OnUsernameChange(newValue))
        }, hint = R.string.name, leadingIcon = R.drawable.user_outlined)
        Spacer(Modifier.height(16.dp))
        AppTextField(value = state.email, onValueChange = { newValue ->
            viewModel.onAction(RegisterAction.OnEmailChange(newValue))
        }, hint = R.string.email)
        Spacer(Modifier.height(16.dp))
        AppTextField(value = state.password, onValueChange = { newValue ->
            viewModel.onAction(RegisterAction.OnPasswordChange(newValue))
        }, hint = R.string.password, leadingIcon = R.drawable.ic_password)
        Spacer(Modifier.height(32.dp))
        Button(
            onClick = {
                viewModel.onAction(RegisterAction.OnRegisterClick)
            },
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 12.dp),
            enabled = !state.isLoading
        ) {
            if (state.isLoading) {
                CircularProgressIndicator()
            } else {
                Text(text = stringResource(id = R.string.register))
            }
        }
        Spacer(Modifier.height(12.dp))
        Text(
            text = stringResource(id = R.string.already_have_an_account), modifier = Modifier
                .padding(8.dp)
                .clickable {
                    navHostController.navigate(Destination.LoginScreen)
                })
    }
}