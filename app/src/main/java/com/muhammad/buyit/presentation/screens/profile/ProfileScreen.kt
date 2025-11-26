package com.muhammad.buyit.presentation.screens.profile

import androidx.compose.foundation.Image
import com.muhammad.buyit.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.muhammad.buyit.presentation.navigation.BottomNavigationBar
import com.muhammad.buyit.presentation.navigation.Destination
import com.muhammad.buyit.presentation.screens.profile.components.ProfileOption
import com.muhammad.domain.utils.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(
    navHostController: NavHostController,
    viewModel: ProfileViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    ObserveAsEvents(viewModel.events) {event ->
        when(event){
            ProfileEvent.OnUserLogout -> {
                navHostController.navigate(Destination.LoginScreen){
                    popUpTo(Destination.ProfileScreen){
                        inclusive = true
                    }
                }
            }
        }
    }
    Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
        BottomNavigationBar(navController = navHostController)
    }) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentPadding = paddingValues,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item("profile_header") {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_user),
                        contentDescription = null,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(
                                CircleShape
                            )
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = state.username,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = state.email,
                        style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.surface)
                    )
                }
            }
            item("notifications") {
                ProfileOption(label = R.string.notifications, icon = R.drawable.notification)
            }
            item("settings") {
                ProfileOption(label = R.string.settings, icon = R.drawable.ic_settings)
            }
            item("contact") {
                ProfileOption(label = R.string.contact, icon = R.drawable.ic_contact)
            }
            item("share") {
                ProfileOption(label = R.string.share, icon = R.drawable.ic_share)
            }
            item("help") {
                ProfileOption(label = R.string.help, icon = R.drawable.help)
            }
            item("logout") {
                Button(
                    onClick = {
                        viewModel.onAction(ProfileAction.OnLogout)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(vertical = 12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onError
                    )
                ) {
                    Text(text = stringResource(R.string.logout))
                }
            }
        }
    }
}