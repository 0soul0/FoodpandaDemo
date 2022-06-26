package com.sideproject.foodpandafake.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sideproject.foodpandafake.model.ShoppingCart
import com.sideproject.foodpandafake.model.StateConsumption
import java.util.*

@Dao
interface ShoppingCartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(menu: ShoppingCart)

    @Query("DELETE FROM table_shoppingCart WHERE stateConsumption== :stateConsumption")
    fun deleteByStateConsumption(stateConsumption: StateConsumption)


    @Query(
        "SELECT *,COUNT(*) as amount,SUM(menuPrice) as menuPrice FROM table_shoppingCart " +
                "WHERE menuNumber == :menuNumber AND stateConsumption == :stateConsumption GROUP BY menuItemId"
    )
    fun getShoppingCart(
        menuNumber: UUID,
        stateConsumption: StateConsumption
    ): LiveData<List<ShoppingCart>>

    @Query("UPDATE table_shoppingCart SET stateConsumption = :stateConsumption WHERE menuNumber==:menuNumber")
    fun updateShoppingCartStateByMenuNumber(menuNumber: UUID, stateConsumption: StateConsumption)

    @Query(
        "SELECT *,COUNT(*) as amount,SUM(menuPrice) as menuPrice FROM table_shoppingCart " +
                "WHERE stateConsumption = :stateConsumption GROUP BY menuItemId ORDER BY menuNumber DESC"
    )
    fun getShoppingCartHistory(stateConsumption: StateConsumption): LiveData<List<ShoppingCart>>
}