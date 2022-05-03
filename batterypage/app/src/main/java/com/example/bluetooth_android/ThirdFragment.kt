package com.example.bluetooth_android

import android.bluetooth.BluetoothDevice
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.bluetooth_android.databinding.FragmentThirdBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ThirdFragment : Fragment() {

    companion object {
        fun newInstance(): ThirdFragment {
            return ThirdFragment()
        }
    }

    private var _binding: FragmentThirdBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentThirdBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backToTwo.setOnClickListener {
            findNavController().navigate(R.id.action_thirdFragment_to_SecondFragment)
        }

        //binding.deviceId.setText("name333333 ")


//        val binding =ActivityMainBinding.inflate(layoutInflater)
//        setContentview(binding.root)
//        binding.textView.text= "hellow"
//        recycler_devices.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)

//        recycler_devices.adapter =

    }

    //创建实体类,存放蓝牙名和蓝牙地址
    class BlueDevice(val deviceName:String,val device: BluetoothDevice)

    //创建RecyclerView适配器
//    class BlueDeviceListAdapter(val deviceList: List<BlueDevice>, val context: Context): RecyclerView.Adapter<BlueDeviceListAdapter.ViewHolder>(){
//        inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
//            val deviceName: TextView = view.findViewById(R.id.device_name)
//            val deviceAddress: TextView = view.findViewById(R.id.device_id)
//        }
//
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//            val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_second,parent,false)
//            val viewHolder = ViewHolder(view)
//
//            //点击事件,启动蓝牙收发Activity
//            viewHolder.itemView.setOnClickListener {
//                val position = viewHolder.adapterPosition
//                val device = deviceList[position]
//                val intent = Intent(context, ::class.java)
//                intent.putExtra(BLUE_ADDRESS, device.device.address)
//                intent.putExtra(BLUE_NAME,device.deviceName)
//                context.startActivity(intent)
//
//            }
//            return viewHolder
//        }
//
//        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//            val device = deviceList[position]
//            holder.deviceName.text = device.deviceName
//            holder.deviceAddress.text = device.device.address
//        }
//
//        override fun getItemCount(): Int {
//            return deviceList.size
//        }
//    }



//    inner class MyAdapter : RecyclerView.Adapter<MyViewHolder>() {
//        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//            holder.itemView.device_image.setImageResource(R.drawable.ic_launcher_foreground)
////            holder.itemView.device_image.setImageResource(ContextCompat.getDrawable(context,R.drawable.ic_launcher_foreground))
////            holder.itemView.device_image.setImageBitmap(BitmapFactory.decodeResource(context!!.resources,ic_launcher_foreground))
//            holder.itemView.device_id.text="id11111"
//            holder.itemView.device_name.text="name11111"
//        }
//
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//            val itemView = LayoutInflater.from(context)
//                .inflate(R.layout.fragment_second,parent,false)
//            return MyViewHolder(itemView)
//        }
//
//        override fun getItemCount(): Int {
//            return 20
//        }
//    }

//    class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
//
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}