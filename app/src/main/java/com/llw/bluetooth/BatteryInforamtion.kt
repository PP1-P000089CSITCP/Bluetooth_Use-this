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

            stringBuilder.append("Battery percentage:\n", batteryPercentage,"%")

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


            stringBuilder.append(temperatureInCelsius,"°C\n")

            val temperatureFahrenheit = ((temperatureInCelsius*1.8)+32).toInt()

            stringBuilder.append(temperatureFahrenheit,"°F\n")

            stringBuilder.append("\nPower source:\n")

            when(intent.getIntExtra(BatteryManager.EXTRA_PLUGGED,-1)){
                BatteryManager.BATTERY_PLUGGED_AC -> stringBuilder.append("AC adpter\n")
                BatteryManager.BATTERY_PLUGGED_USB -> stringBuilder.append("USB connection\n")
                BatteryManager.BATTERY_PLUGGED_WIRELESS -> stringBuilder.append("Wireless connection\n")
                else ->
                    stringBuilder.append("No Power Source\n")
            }

            stringBuilder.append("\nChargin status:\n")

            when(intent.getIntExtra(BatteryManager.EXTRA_STATUS,-1)){
                BatteryManager.BATTERY_STATUS_CHARGING -> stringBuilder.append("Charging\n")
                BatteryManager.BATTERY_STATUS_DISCHARGING -> stringBuilder.append("Not Charging\n")
                BatteryManager.BATTERY_STATUS_FULL -> stringBuilder.append("Full\n")
                BatteryManager.BATTERY_STATUS_NOT_CHARGING -> stringBuilder.append("Not Charging\n")
                BatteryManager.BATTERY_STATUS_UNKNOWN -> stringBuilder.append("Unknown\n")
                else ->
                    stringBuilder.append("Unknown\n")
            }

            val voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE,0).toDouble()/100

            stringBuilder.append("\nVoltage:\n",voltage, "V\n")


            val myTextView = findViewById<TextView>(R.id.battery_information)
            myTextView.text = stringBuilder


        }
    }

    override fun onDestroy() {
        unregisterReceiver(myBroadcastRecevier)
        super.onDestroy()
    }
}