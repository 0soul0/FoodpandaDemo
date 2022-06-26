package com.sideproject.foodpandafake.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "table_shoppingCart")
data class ShoppingCart(
    @PrimaryKey(autoGenerate = true) val id:Int = 0,
    val menuItemId:Int, //菜單品項id
    val menuItemName:String,
    val storeName:String,
    val storeId: UUID,
    val stateConsumption: StateConsumption = StateConsumption.BUYING,
    val menuNumber:UUID,//菜單編號id
    val menuPrice:String,
    @ColumnInfo(name = "amount") val amount:Int = 1
)

enum class StateConsumption{
  BOUGHT,BUYING
}