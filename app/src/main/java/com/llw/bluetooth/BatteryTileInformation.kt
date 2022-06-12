package com.llw.bluetooth
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.os.BatteryManager
import android.os.BatteryManager.*
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.battery_tile_info.*

class BatteryTileInformation : AppCompatActivity() {

    lateinit var button1 : Button
    private lateinit var button2 : Button
    private val batteryManager: BatteryManager by lazy {
        getSystemService(BATTERY_SERVICE) as BatteryManager
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.battery_tile_info)
        val intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        this.registerReceiver(myBroadcastReceiver, intentFilter)

        button1 = findViewById<Button>(R.id.back_button)
        button1.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java )
            startActivity(intent)
        }

        button2 = findViewById<Button>(R.id.more_info)
        button2.setOnClickListener {
            val intent = Intent(this, BatteryInformation::class.java )
            startActivity(intent)
        }
    }

    private val myBroadcastReceiver = object : BroadcastReceiver(){
        @RequiresApi(Build.VERSION_CODES.M)
        @SuppressLint("SetTextI18n")
        override fun onReceive(context: Context, intent: Intent) {

            val batteryPercentage = findViewById<TextView>(R.id.pc_remaining)
            val timeToCharge = findViewById<TextView>(R.id.time_remaining_charge)
            val tempInfo = findViewById<TextView>(R.id.temp_info)
            val currentInfo = findViewById<TextView>(R.id.current_info)
            val voltageInfo = findViewById<TextView>(R.id.voltage_info)
            val inCharge = findViewById<TextView>(R.id.is_charging)

            val pc = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
            batteryPercentage.text = "$pc%"

            val temperatureInCelsius = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0) / 10
            tempInfo.text = "$temperatureInCelsius"
            //val temperatureFahrenheit = ((temperatureInCelsius*1.8)+32).toInt()
            //stringBuilder.append(temperatureFahrenheit,"°F\n")

            val current = BATTERY_PROPERTY_CURRENT_NOW.toDouble()
            currentInfo.text = current.toString()

            val voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE,0).toDouble()/100
            voltageInfo.text = voltage.toString()

            val a = BatteryManager.ACTION_CHARGING

            val isCharging = batteryManager.isCharging
            if (isCharging) {
                inCharge.text = "Time to full charge"
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    val chargeTimeRemaining = batteryManager.computeChargeTimeRemaining()
                    val hours = (chargeTimeRemaining / 3600000).toInt()
                    val minutes = ((chargeTimeRemaining % 3600000) / 60000).toInt()
                    if (hours == 0 && minutes == 0) {
                        inCharge.text = "Battery fully charged"
                    } else {
                        inCharge.text = "Time to full charge"
                        timeToCharge.text = "$hours HRS $minutes MIN "
                    }
                }
            }
            else {
                inCharge.visibility = View.INVISIBLE
                timeToCharge.visibility = View.INVISIBLE
            }
        }
    }

    override fun onDestroy() {
        unregisterReceiver(myBroadcastReceiver)
        super.onDestroy()
    }
}