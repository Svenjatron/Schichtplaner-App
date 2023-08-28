package com.example.shedula_next_try.Activity

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.tech.NfcA
import android.os.Bundle
import android.util.Log
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
import com.google.firebase.FirebaseApp
import androidx.lifecycle.ViewModelProvider
import com.example.shedula_next_try.AdminRegister
import com.example.shedula_next_try.Model.LocalNavController
import com.example.shedula_next_try.Model.MainViewModel

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: MainViewModel
    private var nfcAdapter: NfcAdapter? = null
    private var punchedIn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        val firebase: DatabaseReference = FirebaseDatabase.getInstance().getReference()
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        if (NfcAdapter.ACTION_TECH_DISCOVERED == intent.action) {
            handleNfcIntent(intent)
        }

        setContent { ShedulaNextTryApp(viewModel) }
    }


    override fun onResume() {
        super.onResume()
        val intent = Intent(this, javaClass).apply {
            addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING)
        }
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val intentFilters = arrayOf(IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED))
        val techLists = arrayOf<Array<String>?>(arrayOf(NfcA::class.java.name))

        nfcAdapter?.enableForegroundDispatch(this, pendingIntent, intentFilters, techLists)
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
        Log.d("NFC", "handleNfcIntent called with action: ${intent?.action}")

        if (NfcAdapter.ACTION_TAG_DISCOVERED == intent?.action) {
            Log.d("NFC", "NFC intent detected")

            if (viewModel.punchInTime.value == 0L) {
                viewModel.punchInTime.value = System.currentTimeMillis()
                Log.d("NFC", "Punch in time recorded: ${viewModel.punchInTime.value}")
            } else if (viewModel.punchOutTime.value == 0L) {
                viewModel.punchOutTime.value = System.currentTimeMillis()
                Log.d("NFC", "Punch out time recorded: ${viewModel.punchOutTime.value}")
                calculateTimeDifference()
            } else {
                viewModel.punchInTime.value = 0
                viewModel.punchOutTime.value = 0
                viewModel.hoursWorked.value = ""
                Log.d("NFC", "Times reset")
            }
        }
    }

    private fun calculateTimeDifference() {
        val diff = viewModel.punchOutTime.value!! - viewModel.punchInTime.value!!

        var seconds = diff / 1000
        var minutes = seconds / 60
        val hours = minutes / 60

        minutes %= 60
        seconds %= 60

        viewModel.hoursWorked.value = "$hours:$minutes"
    }

    @Composable
    fun ShedulaNextTryApp(viewModel: MainViewModel) {
        val navController = rememberNavController()
        val context = LocalContext.current

        CompositionLocalProvider(LocalNavController provides navController) {
            NavHost(navController = navController, startDestination = "LoginScreen") {
                composable("LoginScreen") {
                    LoginScreen(viewModel) // Passing the ViewModel to LoginScreen
                }
                composable("LoginManager") {
                    LoginManager(viewModel) // Passing the ViewModel to LoginManager
                }
                composable("ErsteinrichtungScreen") {
                    ErsteinrichtungScreen(viewModel) // Passing the ViewModel to ErsteinrichtungScreen
                }
                composable("TeamManager") {
                    TeamManager(viewModel) // Passing the ViewModel to TeamManager
                }
                composable("EmployeeScreen") {
                    EmployeeScreen(viewModel) // Passing the ViewModel to EmployeeScreen
                }
                composable("AdminScreen") {
                    AdminScreen(viewModel) // Passing the ViewModel to AdminScreen
                }
                composable("ZeiterfassungsScreen") {
                    ZeiterfassungsScreen(viewModel) // Passing the ViewModel to ZeiterfassungsScreen
                }
                composable("NFCKontakt1Screen") {
                    NFCKontakt1Screen(viewModel) // Passing the ViewModel to NFCKontakt1Screen
                }
                composable("KalenderScreen") {
                    KalenderScreen(viewModel) // Passing the ViewModel to KalenderScreen
                }
                composable("TeamVerwaltung") {
                    viewModel.team?.let { team ->
                        TeamVerwaltung(team, context, viewModel) // Passing the ViewModel to TeamVerwaltung
                    }
                }
                composable("AdminRegister") {
                    AdminRegister(context, viewModel) // Passing the ViewModel to AdminRegister
                }
                composable("NfcTimePunchScreen") {
                    NfcTimePunchScreen(viewModel) // Passing the ViewModel to NfcTimePunchScreen
                }
            }
        }

    }

    @Preview
    @Composable
    fun PreviewShedulaNextTryApp() {
        ShedulaNextTryApp(MainViewModel())
    }
}
