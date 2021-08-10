package com.tapakah.hermes

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.content.FileProvider.getUriForFile
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fichier = openFileOutput("test.txt", MODE_APPEND)
        fichier.write("test".toByteArray())
        fichier.close()
        var file = File(filesDir,"test.txt")
        var uri = getUriForFile(this,"com.tapakah.fileprovider", file)
        Log.d("uri", uri.toString())
        //FileOutputStream(File(this.externalCacheDir, "bal")).write("test".toByteArray())
       // FileOutputStream(File(this.getExternalFilesDir("text/plain"), "data_bal")).write("coucou".toByteArray())
        /*var file = File(this.filesDir, "data_bal")
        var uri = FileProvider.getUriForFile(this, "com.tapakah.fileprovider", file)
        //FileOutputStream(File(uri.path))*/

        /*var rvListeBluetooth = findViewById<RecyclerView>(R.id.rvListeBluetooth)
        var bt = BluetoothAdapter.getDefaultAdapter()

        Toast.makeText(this, "send", Toast.LENGTH_SHORT).show()


        if (bt == null) {
            Toast.makeText(this, "Votre appareil n'est pas compatible avec le bluetooth", Toast.LENGTH_LONG).show()
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
        }*/






    }

    fun startService(btDevice: BluetoothDevice){
        val intent = Intent(Intent.ACTION_VIEW)
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        val uri = Uri.parse(getExternalFilesDir("file/*").toString())
        intent.setDataAndType(uri, "*/*")
        startActivity(Intent.createChooser(intent, "Open folder"))

        Intent(this, MyService::class.java).also {
            it.putExtra("EXTRA_DEVICE", btDevice.address)
            startService(it)

        }
    }
}


