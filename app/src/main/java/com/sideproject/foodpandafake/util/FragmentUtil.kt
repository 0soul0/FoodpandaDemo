package com.sideproject.foodpandafake.util

import android.view.View
import com.sideproject.foodpandafake.MainViewModel

object FragmentUtil {

    fun setBottomNavHideOrVisible(mainViewModel:MainViewModel, state: Int= View.VISIBLE) {
        mainViewModel.setBottomNavState(state)
    }
}