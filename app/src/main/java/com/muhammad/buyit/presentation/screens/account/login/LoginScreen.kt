package com.muhammad.buyit.presentation.screens.account.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.muhammad.buyit.R
import com.muhammad.buyit.presentation.components.AppTextField
import com.muhammad.buyit.presentation.navigation.Destination
import com.muhammad.domain.utils.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(navHostController: NavHostController, viewModel: LoginViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }
    val alreadyAccountAnnotatedString = buildAnnotatedString {
        withStyle(
            SpanStyle(
                fontSize = MaterialTheme.typography.bodySmall.fontSize,
                color = MaterialTheme.colorScheme.surface
            )
        ) {
            append(stringResource(R.string.already_have_an_account) + " ")
        }
        pushStringAnnotation(
            tag = stringResource(R.string.register),
            annotation = stringResource(R.string.register)
        )
        withStyle(
            SpanStyle(
                fontSize = MaterialTheme.typography.bodySmall.fontSize,
                color = MaterialTheme.colorScheme.primary
            )
        ) {
            append(stringResource(R.string.register))
        }
    }
    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            LoginEvent.OnLoginSuccess -> {
                navHostController.navigate(Destination.HomeScreen) {
                    popUpTo(Destination.LoginScreen) {
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
            text = stringResource(id = R.string.welcome_back),
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold)
        )
        Spacer(Modifier.height(12.dp))
        Text(
            text = stringResource(R.string.login_description),
            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.surface)
        )
        Spacer(Modifier.height(32.dp))
        AppTextField(value = state.email, onValueChange = { newValue ->
            viewModel.onAction(LoginAction.OnEmailChange(newValue))
        }, hint = R.string.email, keyboardType = KeyboardType.Email, leadingIcon = R.drawable.email)
        Spacer(Modifier.height(20.dp))
        AppTextField(
            value = state.password,
            onValueChange = { newValue ->
                viewModel.onAction(LoginAction.OnPasswordChange(newValue))
            }, onTrailingClick = {
                viewModel.onAction(LoginAction.OnPasswordToggle)
            },
            leadingIcon = R.drawable.ic_password,
            isPasswordTextField = true,
            isPasswordVisible = state.isPasswordVisible,
            trailingIcon = if(state.isPasswordVisible) R.drawable.ic_visibility_off else R.drawable.ic_visibility,
            keyboardType = KeyboardType.Password,
            hint = R.string.password
        )
        Spacer(Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.forget_password),
                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.error)
            )
        }
        Spacer(Modifier.height(32.dp))
        Button(
            onClick = {
                viewModel.onAction(LoginAction.OnLoginClick)
            },
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 12.dp),
            enabled = !state.isLoading
        ) {
            if (state.isLoading) {
                CircularProgressIndicator()
            } else {
                Text(text = stringResource(id = R.string.login))
            }
        }
        Spacer(Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            HorizontalDivider(
                modifier = Modifier
                    .weight(1f)
                    .clip(CircleShape),
                color = MaterialTheme.colorScheme.surfaceVariant,
                thickness = 1.dp
            )
            Text(
                text = stringResource(R.string.or_signin_with),
                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.surface)
            )
            HorizontalDivider(
                modifier = Modifier
                    .weight(1f)
                    .clip(CircleShape),
                color = MaterialTheme.colorScheme.surfaceVariant,
                thickness = 1.dp
            )
        }
        Spacer(Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = {},
                contentPadding = PaddingValues(12.dp),
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_google),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.google),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Button(
                onClick = {},
                contentPadding = PaddingValues(12.dp),
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_facebook),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.facebook),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        Spacer(Modifier.height(16.dp))
        Text(text = alreadyAccountAnnotatedString, modifier = Modifier.pointerInput(Unit) {
            detectTapGestures { offset ->
                textLayoutResult?.let { layout ->
                    val position = layout.getOffsetForPosition(offset)
                    alreadyAccountAnnotatedString.getStringAnnotations(position, position)
                        .firstOrNull()?.let { annotated ->
                            when (annotated.tag) {
                                "Register" -> {
                                    navHostController.navigate(Destination.RegisterScreen)
                                }
                            }
                        }
                }
            }
        }, onTextLayout = { layout ->
            textLayoutResult = layout
        })
    }
}