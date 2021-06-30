package com.tapakah.hermes

import android.R
import android.app.Service
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.IBinder
import android.provider.MediaStore
import android.provider.Telephony.Mms.Part.FILENAME
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class MyService : Service() {
    val TAG = "My Service"
    val bt = BluetoothAdapter.getDefaultAdapter()

    init {
        Log.d(TAG, "Service is running...")
    }
    override fun onBind(intent: Intent?): IBinder? = null

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        var adress = intent?.getStringExtra("EXTRA_DEVICE")
        Log.d(TAG, "hello")
        doSomething(adress)
        return START_STICKY
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun doSomething(adress: String?) {
        if (adress.isNullOrBlank()){

        }
        else{
            try {
                var appareil: BluetoothDevice? = null
                var liste = mutableListOf<BluetoothDevice>()
                var position = 0
                for(i in bt.bondedDevices){
                    liste.add(i)
                    if(i.address == adress){
                        appareil = i
                        position = bt.bondedDevices.indexOf(i)
                    }
                }


                var socket = appareil?.createInsecureRfcommSocketToServiceRecord(liste[position].uuids[0].uuid)
                socket?.connect()

                //Toast.makeText(applicationContext, if (socket.isConnected)"connecté" else "connexion impossible", Toast.LENGTH_SHORT).show()


                var inputS = socket?.inputStream






                //outputStream.write("test".toByteArray())

                //Toast.makeText(context, inputS.read().toString(), Toast.LENGTH_SHORT).show()
                Thread{
                    var buffer = ByteArray(256)
                    var bytes:Int
                    var msg = ""


                    while (true){
                        try {
                            bytes = inputS!!.read(buffer)
                            val temp = String(buffer, 0, bytes)
                            Log.d("buffer", buffer.toString())
                            Log.d("temp", temp)

                            msg += temp.replace(" ", "")
                            if (msg.contains("\n", false)) {

                                val fichier = File(Environment.DIRECTORY_DOCUMENTS, "/balances.txt")
                                fichier.delete()
                                fichier.createNewFile()

                                val fos = FileOutputStream(fichier)
                                Log.d("outputStream", fos.toString())
                                fos.write(msg.toByteArray())
                                fos.close()

                                Log.d("bt", msg)
                                msg = ""


                            }
                        }
                        catch (e: Exception){
                            Log.d(TAG, e.message.toString())
                        }
                    }
                }.start()
            }
            catch (e: IOException){
                Log.d(TAG, e.message.toString())
            }
            catch (e: Exception){
                Log.d(TAG, e.message.toString())
            }
        }

    }

}