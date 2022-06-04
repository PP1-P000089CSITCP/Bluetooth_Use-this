package com.llw.bluetooth
/**
 * @author Yingjian Di
 * @date 2022/4/25 23:20
 * @email s3798345@student.rmit.edu.au
 */

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.*
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.llw.bluetooth.adapter.DeviceAdapter
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import android.widget.Button

class MainActivity : AppCompatActivity() {

    //Bluetooth broadcast receiver
    private var bluetoothReceiver: BluetoothReceiver? = null

    //Bluetooth adapter
    private var bluetoothAdapter: BluetoothAdapter? = null

    //Bluetooth device adapter
    private var mAdapter: DeviceAdapter? = null

    //Variable list
    private var list: MutableList<BluetoothDevice> = mutableListOf()

    //Request code
    private val REQUEST_ENABLE_BLUETOOTH = 1

    // For Local Button
    lateinit var button : Button;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        checkVersion()

        // Jumping to new activity(battery_information)
        button = findViewById<Button>(R.id.button_for_device)
        button.setOnClickListener {
            val intent = Intent(this, BatteryInforamtion::class.java )
            startActivity(intent)
        }

    }

    /**
     * Check Android version
     */
    private fun checkVersion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            permissionsRequest()
        } else {

            initBlueTooth()
        }
    }

    /**
     * Dynamic permission application
     */
    private fun permissionsRequest() {
        val rxPermissions = RxPermissions(this)
        rxPermissions.request(Manifest.permission.ACCESS_FINE_LOCATION)
            .subscribe {
                if (it) {
                    initBlueTooth()
                } else {
                    showMsg("Permission not opened")
                }
            }
    }

    /**
     * Initialize Bluetooth
     */
    private fun initBlueTooth() {
        var intentFilter = IntentFilter()
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND)
        intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED)
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED)
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
        bluetoothReceiver = BluetoothReceiver()
        registerReceiver(bluetoothReceiver, intentFilter)
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

    }

    /**
     * Display prompt message
     */
    private fun showMsg(llw: String) {
        Toast.makeText(this, llw, Toast.LENGTH_SHORT).show()
    }

    /**
     * Broadcast receiver
     */
    inner class BluetoothReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                BluetoothDevice.ACTION_FOUND -> showDevicesData(context, intent)
                BluetoothDevice.ACTION_BOND_STATE_CHANGED -> mAdapter?.changeBondDevice()
                BluetoothAdapter.ACTION_DISCOVERY_STARTED -> loading_lay.visibility = View.VISIBLE
                BluetoothAdapter.ACTION_DISCOVERY_FINISHED -> loading_lay.visibility = View.GONE
                else -> showMsg("UNKNOW")
            }
        }

    }

    /**
     * Display Bluetooth device information
     *
     * @param context
     * @param intent
     */
    private fun showDevicesData(context: Context?, intent: Intent) {

        getBondedDevice()

        val device =
            intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
        if (list.indexOf(device) == -1) {
            if (device.name != null) {
                list.add(device)
            }
        }
        mAdapter = DeviceAdapter(R.layout.item_device_list, list)
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = mAdapter

        mAdapter!!.setOnItemChildClickListener { _, _, position ->
            if (list[position].bondState == BluetoothDevice.BOND_NONE) {
                createOrRemoveBond(1, list[position]) //开始匹配
            } else {
                showDialog(
                    "Are you sure you want to cancel pairing?",
                    DialogInterface.OnClickListener { _, _ ->

                        createOrRemoveBond(2, list[position])
                    })
            }
        }
    }

    /**
     * Get bound device
     */
    private fun getBondedDevice() {
        val pairedDevices = bluetoothAdapter!!.bondedDevices
        if (pairedDevices.size > 0) {
            for (device in pairedDevices) {
                if (list.indexOf(device) == -1) {
                    if (device.name != null) {
                        list.add(device)
                    }
                }
            }
        }
    }

    /**
     * Create or unmatch
     *
     * @param type   Process type 1 match 2 unmatch
     * @param device
     */
    private fun createOrRemoveBond(type: Int, device: BluetoothDevice) {
        var method: Method? = null
        try {
            when (type) {
                1 -> {
                    method = BluetoothDevice::class.java.getMethod("createBond")
                    method.invoke(device)
                }
                2 -> {
                    method = BluetoothDevice::class.java.getMethod("removeBond")
                    method.invoke(device)
                    list.remove(device)
                }
            }
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
    }


    /**
     * Popup
     *
     * @param dialogTitle
     * @param onClickListener
     */
    private fun showDialog(dialogTitle: String, onClickListener: DialogInterface.OnClickListener) {
        val builder =
            AlertDialog.Builder(this)
        builder.setMessage(dialogTitle)
        builder.setPositiveButton("Yes", onClickListener)
        builder.setNegativeButton("No", null)
        builder.create().show()
    }


    /**
     * destroy
     */
    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(bluetoothReceiver)
    }

    /**
     * Scaning bluetooth
     */
    fun scanBluetooth(view: View) {
        if (bluetoothAdapter != null) {
            if (bluetoothAdapter!!.isEnabled) {

                if (mAdapter != null) {
                    list.clear()
                    mAdapter!!.notifyDataSetChanged()
                }
                bluetoothAdapter!!.startDiscovery()
            } else {
                val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(intent, REQUEST_ENABLE_BLUETOOTH)
            }
        } else {
            showMsg("Your device does not support Bluetooth")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }



}

