package com.example.shedula_next_try.Activity

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.tooling.preview.Preview
import com.example.shedula_next_try.View.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.MutableLiveData
import com.google.firebase.FirebaseApp
import androidx.lifecycle.ViewModelProvider
import com.example.shedula_next_try.AdminRegister
import com.example.shedula_next_try.Model.LocalNavController
import com.example.shedula_next_try.Model.MainViewModel
import com.example.shedula_next_try.Model.Role
import com.example.shedula_next_try.Model.User

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: MainViewModel
    private var admin: User? = null
    private var nfcAdapter: NfcAdapter? = null
    private var punchInTime = MutableLiveData<Long>(0)
    private var punchOutTime = MutableLiveData<Long>(0)
    private var hoursWorked = MutableLiveData<String>("")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        val firebase : DatabaseReference = FirebaseDatabase.getInstance().getReference()
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)

        setContent {
            ShedulaNextTryApp(viewModel)
        }
    }
    override fun onResume() {
        super.onResume()
        val intent = Intent(this, javaClass).apply {
            addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING)
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        val intentFilter = IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED)

        nfcAdapter?.enableForegroundDispatch(this, pendingIntent, arrayOf(intentFilter), null)
    }
    override fun onPause() {
        super.onPause()
        nfcAdapter?.disableForegroundDispatch(this)
    }
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleNfcIntent(intent)
    }
    private fun handleNfcIntent(intent: Intent?) {
        if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent?.action || NfcAdapter.ACTION_TAG_DISCOVERED == intent?.action) {
            if (punchInTime.value == 0L) {
                punchInTime.value = System.currentTimeMillis()
                // ... Communicate punch in
            } else if (punchOutTime.value == 0L) {
                punchOutTime.value = System.currentTimeMillis()
                // ... Communicate punch out
                calculateTimeDifference()
            } else {
                // Reset if both times are already recorded
                punchInTime.value = 0
                punchOutTime.value = 0
                hoursWorked.value = ""
                // ... Communicate reset
            }
        }
    }

    private fun calculateTimeDifference() {
        val diff = punchOutTime.value!! - punchInTime.value!!

        var seconds = diff / 1000
        var minutes = seconds / 60
        val hours = minutes / 60

        minutes %= 60
        seconds %= 60

        hoursWorked.value = "$hours:$minutes"
    }

}


@Composable
fun ShedulaNextTryApp(viewModel: MainViewModel) {
    val navController = rememberNavController()
    val context = LocalContext.current

    CompositionLocalProvider(LocalNavController provides navController) {
        NavHost(navController = navController, startDestination = "LoginScreen") {
            composable("LoginScreen") {
                LoginScreen()
            }
            composable("LoginManager") {
                LoginManager()
            }
            composable("ErsteinrichtungScreen") {
                ErsteinrichtungScreen()
            }
            composable("TeamManager") {
                TeamManager()
            }
            composable("EmployeeScreen") {
                EmployeeScreen()
            }
            composable("AdminScreen") {
                AdminScreen()
            }
            composable("ZeiterfassungsScreen") {
                ZeiterfassungsScreen()
            }
            composable("NFC_neu1") {
                NFCKontakt1Screen()
            }
            composable("KalenderScreen") {
                KalenderScreen(viewModel)
            }
            composable("TeamVerwaltung") {
                viewModel.team?.let { team ->
                    TeamVerwaltung(team, context, viewModel)
                }
            }
            composable("AdminRegister") {
                AdminRegister(context, viewModel)
            }
            composable("NfcTimePunchScreen") {
                NfcTimePunchScreen(viewModel)
            }
        }
    }
}

@Preview
@Composable
fun PreviewShedulaNextTryApp() {
    ShedulaNextTryApp(MainViewModel())
}


