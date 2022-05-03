// package com.example.bluetooth_android
//
// import android.content.Context
// import android.graphics.Bitmap
// import android.graphics.Canvas
// import android.graphics.Paint
// import android.view.View
// import androidx.core.content.res.ResourcesCompat
//
// class BatteryIcon (context: Context) : View(context) {
//
// private lateinit var canvas1 : Canvas
// private lateinit var bitmap1 : Bitmap
//
// private val bgColour = ResourcesCompat.getColor(resources, R.color.purple_200, null)
// private val fgColour = ResourcesCompat.getColor(resources, R.color.white, null)
//
// private val paint = Paint().apply {
// color = fgColour
// style = Paint.Style.STROKE
// isAntiAlias = true
// textSize = 20f
// textAlign = Paint.Align.CENTER
// }
//
// override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
// super.onSizeChanged(w, h, oldw, oldh)
// if(::bitmap1.isInitialized)bitmap1.recycle()
// bitmap1 = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
// canvas1 = Canvas(bitmap1)
// canvas1.drawColor(bgColour)
// }
//
// override fun onDraw(canvas: Canvas?) {
// super.onDraw(canvas)
//
// canvas?.drawBitmap(bitmap1, 0f, 0f, paint)
// canvas?.drawRect(20f, 20f, 30f, 20f, paint)
//
// val xpos = (canvas?.width)?.div(2)
// val ypos = (canvas?.height)?.div(2)
// if (xpos != null && ypos != null) {
// canvas?.drawText("ALOHA", xpos.toFloat(), ypos.toFloat(), paint)
// }
//
// }
//
// }