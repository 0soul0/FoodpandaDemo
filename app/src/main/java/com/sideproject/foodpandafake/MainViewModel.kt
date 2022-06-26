package com.sideproject.foodpandafake

import android.content.Context
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sideproject.foodpandafake.db.AppDb
import com.sideproject.foodpandafake.db.DefaultDb
import com.sideproject.foodpandafake.model.Menu
import com.sideproject.foodpandafake.model.ShoppingCart
import com.sideproject.foodpandafake.model.StateConsumption
import com.sideproject.foodpandafake.model.Store
import com.sideproject.foodpandafake.repo.FoodPandaRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val foodPandaRepo: FoodPandaRepo,
    private val appDb: AppDb
) : ViewModel() {

    private val _bottomNavState = MutableLiveData(View.VISIBLE)
    val bottomNavState: LiveData<Int> = _bottomNavState

    private var _foodPanda = MutableLiveData<List<Store>>()
    private var foodPanda: LiveData<List<Store>> = _foodPanda

    var visitStoreGPSPosition = emptyList<Double>()

    var menuNumber: UUID? = null
        get() {
            if (field == null) {
                field = UUID.randomUUID()
            }
            return field
        }
    private var _storeId: UUID? = null
        get() {
            if (field == null) {
                field = UUID.randomUUID()
            }
            return field
        }

    fun getStore(context: Context): LiveData<List<Store>> {
        viewModelScope.launch(Dispatchers.IO) {
            _foodPanda.postValue(foodPandaRepo.getStringOfWebContent(context))
        }
        return foodPanda
    }


    fun getMenu(position: Int, storeId: UUID): LiveData<List<Menu>> =
        runBlocking {
            initMenu(storeId)
            val page = position % 3 * 4
            appDb.menuDao.getCardByPosition(page)
        }

    private suspend fun initMenu(storeId: UUID) {
        withContext(Dispatchers.IO) {
            if (appDb.menuDao.getCount() == 0) {
                appDb.menuDao.insert(DefaultDb().initMenu())
            }
            //初始化訂單
            if (_storeId != storeId) {
                _storeId = storeId
                menuNumber = UUID.randomUUID()
                appDb.shoppingCartDao.deleteByStateConsumption(StateConsumption.BUYING)

            }
        }
    }

    fun setBottomNavState(state: Int) {
        _bottomNavState.value = state
    }

    fun addShoppingCart(shoppingCart: ShoppingCart) {
        viewModelScope.launch(Dispatchers.IO) {
            appDb.shoppingCartDao.insert(shoppingCart)
        }
    }


    fun getShoppingCart(
        stateConsumption: StateConsumption
    ): LiveData<List<ShoppingCart>> = runBlocking {
        appDb.shoppingCartDao.getShoppingCart(menuNumber!!, stateConsumption)
    }

    fun getShoppingCartHistory(stateConsumption: StateConsumption): LiveData<List<ShoppingCart>> = runBlocking {
        appDb.shoppingCartDao.getShoppingCartHistory(stateConsumption)
    }

    fun updateShoppingCartStateByMenuNumber(stateConsumption: StateConsumption) {
        viewModelScope.launch(Dispatchers.IO) {
            appDb.shoppingCartDao.updateShoppingCartStateByMenuNumber(menuNumber!!, stateConsumption)
        }
    }


}