package com.sideproject.foodpandafake.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sideproject.foodpandafake.model.Menu

@Dao
interface MenuDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(menu: List<Menu>)

    @Query("SELECT * FROM table_menu Limit :page,4")
    fun getCardByPosition(page: Int): LiveData<List<Menu>>

    @Query("SELECT COUNT(*) FROM table_menu")
    fun getCount():Int

}