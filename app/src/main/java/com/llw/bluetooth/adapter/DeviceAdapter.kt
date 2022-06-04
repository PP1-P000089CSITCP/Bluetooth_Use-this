package com.llw.bluetooth.adapter
/**
 * @author Yingjian Di
 * @date 2022/4/20 10:01
 * @email s3798345@student.rmit.edu.au
 */

import android.bluetooth.BluetoothClass
import android.bluetooth.BluetoothClass.Device.*
import android.bluetooth.BluetoothClass.Device.Major.*
import android.bluetooth.BluetoothDevice
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.llw.bluetooth.R

/**
 * Bluetooth device adapter
 */
class DeviceAdapter(layoutResId: Int, data: MutableList<BluetoothDevice>?) :
    BaseQuickAdapter<BluetoothDevice, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder?, item: BluetoothDevice?) {
        val tvName = helper!!.getView<TextView>(R.id.tv_name)
        val icon = helper.getView<ImageView>(R.id.iv_device_type)

        //Set icons according to device type
        getDeviceType(item!!.bluetoothClass.majorDeviceClass, icon)

        tvName.text = if (item.name == null) "无名" else item.name

        //Bluetooth device binding status judgment
        val tvState = helper!!.getView<TextView>(R.id.tv_bond_state)
        tvState.text = when (item.bondState) {
            10 -> "Unpaired"
            11 -> "Pairing..."
            12 -> "Paired"
            else -> "Unpaired"
        }
        //Add item click event
        helper.addOnClickListener(R.id.item_device)

    }

    /**
     * Set icons by type
     * @param type Type code
     * @param icon ICON
     */
    private fun getDeviceType(type: Int, icon: ImageView) {
        when (type) {
            AUDIO_VIDEO_HEADPHONES,
            AUDIO_VIDEO_WEARABLE_HEADSET,
            AUDIO_VIDEO_HANDSFREE,
            AUDIO_VIDEO
            -> icon.setImageResource(R.mipmap.icon_headset)
            COMPUTER
            -> icon.setImageResource(R.mipmap.icon_computer)
            PHONE
            -> icon.setImageResource(R.mipmap.icon_phone)
            HEALTH
            -> icon.setImageResource(R.mipmap.icon_health)
            AUDIO_VIDEO_CAMCORDER,
            AUDIO_VIDEO_VCR
            -> icon.setImageResource(R.mipmap.icon_vcr)
            AUDIO_VIDEO_CAR_AUDIO
            -> icon.setImageResource(R.mipmap.icon_car)
            AUDIO_VIDEO_LOUDSPEAKER
            -> icon.setImageResource(R.mipmap.icon_loudspeaker)
            AUDIO_VIDEO_MICROPHONE
            -> icon.setImageResource(R.mipmap.icon_microphone)
            AUDIO_VIDEO_PORTABLE_AUDIO
            -> icon.setImageResource(R.mipmap.icon_printer)
            AUDIO_VIDEO_SET_TOP_BOX
            -> icon.setImageResource(R.mipmap.icon_top_box)
            AUDIO_VIDEO_VIDEO_CONFERENCING
            -> icon.setImageResource(R.mipmap.icon_meeting)
            AUDIO_VIDEO_VIDEO_DISPLAY_AND_LOUDSPEAKER
            -> icon.setImageResource(R.mipmap.icon_tv)
            AUDIO_VIDEO_VIDEO_GAMING_TOY
            -> icon.setImageResource(R.mipmap.icon_game)
            AUDIO_VIDEO_VIDEO_MONITOR
            -> icon.setImageResource(R.mipmap.icon_wearable_devices)
            else -> icon.setImageResource(R.mipmap.icon_bluetooth)
        }
    }

    /**
     * Refresh adapter
     */
    fun changeBondDevice() {
        notifyDataSetChanged()
    }
}