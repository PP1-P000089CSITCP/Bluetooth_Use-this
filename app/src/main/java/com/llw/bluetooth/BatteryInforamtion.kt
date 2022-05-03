package com.llw.bluetooth

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class BatteryInforamtion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.battery_information)

        val intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)

        this.registerReceiver(myBroadcastRecevier, intentFilter)
    }
    private val myBroadcastRecevier = object : BroadcastReceiver(){
        override fun onReceive(context: Context, intent: Intent) {
            val stringBuilder = StringBuilder()

            val batteryPercentage = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)

            stringBuilder.append("Battery percentage:\n", batteryPercentage)

            stringBuilder.append("\n")


            stringBuilder.append("\nBattery Heath\n")

            when(intent.getIntExtra(BatteryManager.EXTRA_HEALTH,0 ) ){
                BatteryManager.BATTERY_HEALTH_OVERHEAT -> stringBuilder.append("Over Heat\n")
                BatteryManager.BATTERY_HEALTH_COLD -> stringBuilder.append("Cold\n")
                BatteryManager.BATTERY_HEALTH_DEAD -> stringBuilder.append("Dead\n")
                BatteryManager.BATTERY_HEALTH_GOOD -> stringBuilder.append("Good\n")
                BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE -> stringBuilder.append("Over voltage\n")
                BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE -> stringBuilder.append("failure\n")
                else ->
                    stringBuilder.append("Unknown\n")
            }


            stringBuilder.append("\nTemperature:\n")

            val temperatureInCelsius = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0) / 10


            stringBuilder.append(temperatureInCelsius)

            val myTextView = findViewById<TextView>(R.id.battery_information)
            myTextView.text = stringBuilder


        }
    }
}