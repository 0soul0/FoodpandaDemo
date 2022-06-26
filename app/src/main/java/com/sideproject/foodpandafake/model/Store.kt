package com.sideproject.foodpandafake.model

import android.graphics.Bitmap
import java.util.*


data class Store(
    val id: UUID = UUID.randomUUID(),
    val position: Int,
    val name: String = "",
    val imgUrl: String = "",
    val img:Bitmap?,
    val menuUrl: String = "",
    val gpsPosition:List<Double>
)
