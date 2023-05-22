package com.example.shedula_next_try.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.tooling.preview.Preview
import com.example.shedula_next_try.View.NFCKontakt1Screen
import com.example.shedula_next_try.View.AdminScreen
import com.example.shedula_next_try.View.EmployeeScreen
import com.example.shedula_next_try.View.ErsteinrichtungScreen
import com.example.shedula_next_try.View.KalenderScreen
import com.example.shedula_next_try.View.LoginManager
import com.example.shedula_next_try.View.LoginScreen
import com.example.shedula_next_try.View.TeamManager
import com.example.shedula_next_try.View.ZeiterfassungsScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShedulaNextTryApp()
        }
    }
}

@Composable
fun ShedulaNextTryApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "LoginScreen") {
        composable("LoginScreen") {
            LoginScreen(navController)
        }
        composable("LoginManager") {
            LoginManager(navController)
        }
        composable("ErsteinrichtungScreen") {
            ErsteinrichtungScreen(navController)
        }
        composable("TeamManager") {
            TeamManager(navController)
        }
        composable("EmployeeScreen") {
            EmployeeScreen(navController)
        }
        composable("AdminScreen") {
            AdminScreen(navController)
        }
        composable("ZeiterfassungsScreen") {
            ZeiterfassungsScreen(navController)
        }
        composable("NFCKontakt1Screen") {
            NFCKontakt1Screen(navController)
        }
        composable("KalenderScreen") {
            KalenderScreen(navController)
        }



    }
}

@Preview
@Composable
fun PreviewShedulaNextTryApp() {
    ShedulaNextTryApp()
}





