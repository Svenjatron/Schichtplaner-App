import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.NfcEvent
import android.nfc.NfcAdapter.CreateNdefMessageCallback
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.shedula_next_try.R
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), CreateNdefMessageCallback {
    private lateinit var btnPunchIn: Button
    private lateinit var btnPunchOut: Button
    private lateinit var tvTimeDifference: TextView

    private var punchInTime: Long = 0
    private var punchOutTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnPunchIn = findViewById(R.id.btnPunchIn)
        btnPunchOut = findViewById(R.id.btnPunchOut)
        tvTimeDifference = findViewById(R.id.tvTimeDifference)

        btnPunchIn.setOnClickListener {
            punchInTime = System.currentTimeMillis()
            updateDisplay()
        }

        btnPunchOut.setOnClickListener {
            punchOutTime = System.currentTimeMillis()
            updateDisplay()
            calculateTimeDifference()
        }

        val nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        nfcAdapter.setNdefPushMessageCallback(this, this)
    }

    private fun updateDisplay() {
        val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val punchInTimeString = sdf.format(Date(punchInTime))
        val punchOutTimeString = sdf.format(Date(punchOutTime))

        val displayText = "Punch In: $punchInTimeString\nPunch Out: $punchOutTimeString"
        tvTimeDifference.text = displayText
    }

    private fun calculateTimeDifference() {
        val diffInMillis = punchOutTime - punchInTime
        val diffInHours = diffInMillis / (1000 * 60 * 60)

        tvTimeDifference.append("\n\nTime Difference: $diffInHours hours")
    }

    override fun createNdefMessage(event: NfcEvent): NdefMessage {

        return NdefMessage(NdefRecord.createTextRecord("Punch In/Out", "Worked!"))
    }
}
