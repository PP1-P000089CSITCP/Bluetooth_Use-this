package com.llw.bluetooth
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.BatteryManager.BATTERY_PROPERTY_CURRENT_NOW
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.llw.bluetooth.R
import kotlinx.android.synthetic.main.battery_tile_info.*

class BatteryTileInformation : AppCompatActivity() {
    private val batteryManager: BatteryManager by lazy {
        getSystemService(BATTERY_SERVICE) as BatteryManager
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.battery_tile_info)
        val intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        this.registerReceiver(myBroadcastReceiver, intentFilter)
    }

    private val myBroadcastReceiver = object : BroadcastReceiver(){

        @SuppressLint("SetTextI18n")
        override fun onReceive(context: Context, intent: Intent) {

            val batteryPercentage = findViewById<TextView>(R.id.percent_info)
            val batteryTime = findViewById<TextView>(R.id.charge_info)
            val tempInfo = findViewById<TextView>(R.id.temp_info)
            val currentInfo = findViewById<TextView>(R.id.current_info)
            val voltageInfo = findViewById<TextView>(R.id.voltage_info)

            val pc = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
            percent_info.text = "$pc%"

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val chargeTimeRemaining = batteryManager.computeChargeTimeRemaining()
                val hours = chargeTimeRemaining / 60000
                val minutes = chargeTimeRemaining % 60000
                batteryTime.text = "$hours HRS $minutes MIN "
            }

            val capacity = BatteryManager.BATTERY_PROPERTY_CAPACITY


            val temperatureInCelsius = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0) / 10
            tempInfo.text = "$temperatureInCelsius°C"
            //val temperatureFahrenheit = ((temperatureInCelsius*1.8)+32).toInt()
            //stringBuilder.append(temperatureFahrenheit,"°F\n")

            val current = BATTERY_PROPERTY_CURRENT_NOW / 1000
            currentInfo.text = current.toString() + "A"

            val voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE,0).toDouble()/100
            voltageInfo.text = voltage.toString() + "V"
        }
    }

    override fun onDestroy() {
        unregisterReceiver(myBroadcastReceiver)
        super.onDestroy()
    }
}