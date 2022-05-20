package com.llw.bluetooth

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.lang.Math.abs

/*
    Part 3 _ Getting information from local device
    Author: Yinglin Duan
    Date : 2022/05/19
 */

class BatteryInforamtion : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.battery_information)

        val intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)

        this.registerReceiver(myBroadcastRecevier, intentFilter)

        /*
        button = findViewById<Button>(R.id.button_back)
        button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java )
            startActivity(intent)
        }

         */


    }
    private val myBroadcastRecevier = object : BroadcastReceiver(){
        override fun onReceive(context: Context, intent: Intent) {

            // Adding information in there
            val stringBuilder = StringBuilder()

            // Getting battery level
            val batteryPercentage = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)


            // Adding first information
            stringBuilder.append("Battery Percentage:\n", batteryPercentage,"%")


            val batteryManager : BatteryManager = applicationContext.getSystemService(Context.BATTERY_SERVICE) as BatteryManager


            /*
            //Current Cap
            val valueOfCap = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
            // Remaining battery capacity in microampere-hours mAH
            val valueOfChargeCounter = batteryManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER)
            // Cost
            val valueOfCost = batteryManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW)

            // get remaining times(Mins)
            val remainTime = (valueOfChargeCounter / valueOfCost) * 60

            stringBuilder.append(valueOfCap,"\n")
            stringBuilder.append(valueOfChargeCounter,"\n")
            stringBuilder.append(valueOfCost,"\n")
            stringBuilder.append(remainTime,"\n")
            */



            stringBuilder.append("\n")


            stringBuilder.append("\nBattery Heath\n")


            // Getting battery health status
            when(intent.getIntExtra(BatteryManager.EXTRA_HEALTH,0 ) ){
                BatteryManager.BATTERY_HEALTH_OVERHEAT -> stringBuilder.append("Over Heat\n")
                BatteryManager.BATTERY_HEALTH_COLD -> stringBuilder.append("Cold\n")
                BatteryManager.BATTERY_HEALTH_DEAD -> stringBuilder.append("Dead\n")
                BatteryManager.BATTERY_HEALTH_GOOD -> stringBuilder.append("Good\n")
                BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE -> stringBuilder.append("Over Voltage\n")
                BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE -> stringBuilder.append("Failure\n")
                else ->
                    stringBuilder.append("Unknown\n")
            }


            stringBuilder.append("\nTemperature:\n")


            // Getting battery temp
            val temperatureInCelsius = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0) / 10


            stringBuilder.append(temperatureInCelsius,"°C\n")

            val temperatureFahrenheit = ((temperatureInCelsius*1.8)+32).toInt()

            stringBuilder.append(temperatureFahrenheit,"°F\n")

            stringBuilder.append("\nPower Source:\n")


            // Battery plugged information
            when(intent.getIntExtra(BatteryManager.EXTRA_PLUGGED,-1)){
                BatteryManager.BATTERY_PLUGGED_AC -> stringBuilder.append("AC Adapter\n")
                BatteryManager.BATTERY_PLUGGED_USB -> stringBuilder.append("USB Connection\n")
                BatteryManager.BATTERY_PLUGGED_WIRELESS -> stringBuilder.append("Wireless Connection\n")
                else ->
                    stringBuilder.append("No Power Source\n")
            }

            stringBuilder.append("\nCharging Status:\n")


            //Battery charging information
            when(intent.getIntExtra(BatteryManager.EXTRA_STATUS,-1)){
                BatteryManager.BATTERY_STATUS_CHARGING -> stringBuilder.append("Charging\n")
                BatteryManager.BATTERY_STATUS_DISCHARGING -> stringBuilder.append("Not Charging\n")
                BatteryManager.BATTERY_STATUS_FULL -> stringBuilder.append("Full\n")
                BatteryManager.BATTERY_STATUS_NOT_CHARGING -> stringBuilder.append("Not Charging\n")
                BatteryManager.BATTERY_STATUS_UNKNOWN -> stringBuilder.append("Unknown\n")
                else ->
                    stringBuilder.append("Unknown\n")
            }

            // Battery max Voltage

            val voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE,0).toDouble()/100

            stringBuilder.append("\nVoltage:\n",voltage, "V\n")


            // Calling layout.
            val myTextView = findViewById<TextView>(R.id.battery_information)
            myTextView.text = stringBuilder


        }
    }

    override fun onDestroy() {
        unregisterReceiver(myBroadcastRecevier)
        super.onDestroy()
    }

}