package ru.ptweb.terminal

import android.bluetooth.BluetoothDevice
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class BluetoothClient(private val liveData: MutableLiveData<String>, device: BluetoothDevice): Thread() {
    private val socket = device.createRfcommSocketToServiceRecord(device.uuids.get(0).uuid)

    override fun run() {
        Log.i("client", "Connecting")
        this.socket.connect()

        Log.i("client", "Sending")
        val outputStream = this.socket.outputStream
        val inputStream = this.socket.inputStream

        try {
            val available = inputStream.available()
            //val bytes = ByteArray(available)

            val buffer = ByteArray(1024)
            var bytes: Int

            while(true) {
                Log.i("client", "Reading")
                //inputStream.read(bytes, 0, available)
                //val text = String(bytes)
                Log.i("client", "Message received")
                //Log.i("client", text)
                //activity.appendText(text)

                bytes = inputStream.read(buffer)
                val readMessage = String(buffer, 0, bytes)
                this.liveData.postValue(readMessage)
            }
        } catch (e: Exception) {
            Log.e("client", "Cannot read data", e)
        } finally {
            inputStream.close()
            outputStream.close()
            this.socket.close()
        }

        /*
        try {
            outputStream.write("QQQQQQ____QQQQQQ".toByteArray())
            outputStream.flush()
            Log.i("client", "Sent")
        } catch(e: Exception) {
            Log.e("client", "Cannot send", e)
        } finally {
            outputStream.close()
            inputStream.close()
            this.socket.close()
        }*/
    }
}