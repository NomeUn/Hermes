package com.tapakah.hermes

import android.app.Instrumentation
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.KeyEvent
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.os.HandlerCompat.postDelayed
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.security.Key



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var rvListeBluetooth = findViewById<RecyclerView>(R.id.rvListeBluetooth)
        var bt = BluetoothAdapter.getDefaultAdapter()

        Toast.makeText(this, "send", Toast.LENGTH_SHORT).show()


        if (bt == null) {
            Toast.makeText(this,"Votre appareil n'est pas compatible avec le bluetooth", Toast.LENGTH_LONG).show()
        }

        if (!bt.isEnabled) {
            if (bt.enable()) {

                Toast.makeText(this, "Bluetooth activé", Toast.LENGTH_LONG).show()
            }
        }

        var devices = bt.bondedDevices
        if (devices.isEmpty()){
            Toast.makeText(this, "Aucun appareil apparayé en Bluetooth", Toast.LENGTH_SHORT).show()
        }
        else {
            var btAdapter = BtAdapter(devices, ::startService)
            rvListeBluetooth.adapter = btAdapter
            rvListeBluetooth.layoutManager = LinearLayoutManager(this)
        }




    }

    fun startService(btDevice: BluetoothDevice){
        Intent(this, MyService::class.java).also {
            it.putExtra("EXTRA_DEVICE", btDevice.address)
            startService(it)

        }
    }
}


