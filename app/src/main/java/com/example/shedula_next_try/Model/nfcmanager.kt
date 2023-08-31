package com.example.shedula_next_try.Model

import android.content.Intent
import android.nfc.NfcAdapter
import android.util.Log

class NFCManager(private val viewModel: MainViewModel) {

    fun handleNfcIntent(intent: Intent?) {
        Log.d("NFC", "handleNfcIntent called with action: ${intent?.action}")

        if (NfcAdapter.ACTION_TAG_DISCOVERED == intent?.action) {
            Log.d("NFC", "NFC intent detected")
            viewModel.handleNfcTagScanned()
        }
    }
}

