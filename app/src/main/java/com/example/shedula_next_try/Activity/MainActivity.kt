package com.example.shedula_next_try.Activity

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.tech.NfcA
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
import com.google.firebase.FirebaseApp
import androidx.lifecycle.ViewModelProvider
import com.example.shedula_next_try.AdminRegister
import com.example.shedula_next_try.Model.LocalNavController
import com.example.shedula_next_try.Model.MainViewModel
import com.example.shedula_next_try.Model.NFCManager

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: MainViewModel
    private var nfcAdapter: NfcAdapter? = null
    private lateinit var nfcManager: NFCManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        val firebase: DatabaseReference = FirebaseDatabase.getInstance().getReference()
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)

        nfcManager = NFCManager(viewModel)
        if (NfcAdapter.ACTION_TECH_DISCOVERED == intent.action) {
            nfcManager.handleNfcIntent(intent)
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
        nfcManager.handleNfcIntent(intent)
    }

    @Composable
    fun ShedulaNextTryApp(viewModel: MainViewModel) {
        val navController = rememberNavController()
        val context = LocalContext.current

        CompositionLocalProvider(LocalNavController provides navController) {
            NavHost(navController = navController, startDestination = "LoginScreen") {
                composable("LoginScreen") {
                    LoginScreen(viewModel)
                }
                composable("LoginManager") {
                    LoginManager(viewModel)
                }
                composable("ErsteinrichtungScreen") {
                    ErsteinrichtungScreen(viewModel)
                }
                composable("TeamManager") {
                    TeamManager(viewModel)
                }
                composable("EmployeeScreen") {
                    EmployeeScreen(viewModel)
                }
                composable("AdminScreen") {
                    AdminScreen(viewModel)
                }
                composable("ZeiterfassungsScreen") {
                    ZeiterfassungsScreen(viewModel)
                }
                composable("NFCKontakt1Screen") {
                    NFCKontakt1Screen(viewModel)
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
}

