package ru.ptweb.terminal

import android.bluetooth.BluetoothAdapter
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val liveData = MutableLiveData<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        liveData.observe(this, Observer {
            outputText.text = "${outputText.text}${it}"
        })

        connect()
    }

    private fun connect(){
        Log.i("activity", "connecting")
        if (BluetoothAdapter.getDefaultAdapter() == null) {
            Snackbar.make(outputText, "Bluetooth is disabled", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()

        } else {
            for (device in BluetoothAdapter.getDefaultAdapter().bondedDevices) {
                Log.i("activity", device.address)
                BluetoothClient(liveData, device).start()
                outputText.text = ""
                break
            }
        }
    }
}
