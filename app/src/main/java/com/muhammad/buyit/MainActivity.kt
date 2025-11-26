package com.muhammad.buyit

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.muhammad.buyit.data.UserSession
import com.muhammad.buyit.presentation.navigation.AppNavigation
import com.muhammad.buyit.presentation.theme.BuyItTheme
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT,Color.TRANSPARENT
            ), navigationBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT,Color.TRANSPARENT
            )
        )
        setContent {
            val userSession: UserSession by inject<UserSession>()
            BuyItTheme {
                val navHostController = rememberNavController()
                AppNavigation(navHostController = navHostController, userSession = userSession)
            }
        }
    }
}