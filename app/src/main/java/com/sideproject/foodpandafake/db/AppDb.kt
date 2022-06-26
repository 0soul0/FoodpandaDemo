package com.sideproject.foodpandafake.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sideproject.foodpandafake.model.Menu
import com.sideproject.foodpandafake.model.ShoppingCart

@Database(
    entities = [Menu::class, ShoppingCart::class],
    version = 1,
    exportSchema = false
)
abstract class AppDb : RoomDatabase() {
    abstract val menuDao: MenuDao
    abstract val shoppingCartDao: ShoppingCartDao
}