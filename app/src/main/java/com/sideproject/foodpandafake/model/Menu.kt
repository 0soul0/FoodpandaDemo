package com.sideproject.foodpandafake.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "table_menu")
data class Menu(
    @PrimaryKey(autoGenerate = true) val id: Int=0,
    val price: String,
    val storeId: UUID?=null,
    val menuName: String,
    val storeName: String?=null,
    val imgUrl: String
)

