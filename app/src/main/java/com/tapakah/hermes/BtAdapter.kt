 package com.tapakah.hermes

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context

import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.IOException

class BtAdapter(
    var listeAppareils: MutableSet<BluetoothDevice>,
    val startService: (btDevice: BluetoothDevice) -> Unit
) : RecyclerView.Adapter<BtAdapter.BtViewHolder>() {

    lateinit var socket: BluetoothSocket
    lateinit var listeEditText: MutableList<EditText>
    var liste: MutableList<BluetoothDevice> = mutableListOf()
    //var bal = ctx.getSharedPreferences("pref", AppCompatActivity.MODE_PRIVATE).getString("bal", "")?.let { BDDHandler(ctx).getBalance(it) }


    inner class BtViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BtViewHolder {
        for (i in listeAppareils){
            liste.add(i)
        }
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bt, parent, false)
        return BtViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: BtViewHolder, position: Int) {
        holder.itemView.apply {
            if (position%2 == 0){
                this.setBackgroundColor(Color.LTGRAY)
            }
            else{
                this.setBackgroundColor(Color.WHITE)
            }

            findViewById<TextView>(R.id.tvBt).text = liste[position].name
            this.setOnClickListener {
                startService(liste[position])
                Toast.makeText(context, "Service lancé", Toast.LENGTH_LONG).show()
            }

        }
    }


    override fun getItemCount(): Int {
        return listeAppareils.size
    }

    fun getPoids(msg:String): String? {
        Log.d("msg",msg)
        var temp = msg.dropLastWhile { ! it.isDigit()  }.takeLastWhile { it.isDigit() || it == '.' || it == ','}
        Log.d("temp1",temp)
        /*if (bal != null){
            while (temp.toFloat() > bal!!.porte){
                temp = temp.drop(1)
                Log.d("poids", temp)
            }
        }*/
        Log.d("temp2",temp)
        temp = temp.dropWhile { it == '0' }
        Log.d("temp3",temp)
        if(temp.contains('.', true) || temp.contains(',',true)){
            temp = temp.dropLastWhile { it == '0' }
        }
        Log.d("temp4",temp)
        return temp
    }
}