package com.sideproject.foodpandafake.repo

import android.content.Context
import android.graphics.Bitmap
import com.bumptech.glide.Glide
import com.sideproject.foodpandafake.hilt.Config
import com.sideproject.foodpandafake.model.Store
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import kotlin.collections.ArrayList
import kotlin.random.Random


class FoodPandaRepo(private val foodPanda: FoodPanda) {

    suspend fun getStringOfWebContent(context: Context): List<Store>? {
        val res = foodPanda.getStringOfWebContent()
        if (res.isSuccessful) {
            return analyzeFoodPandaHtmlStore(res.body(), context, listOf(24.1517558, 120.6774454))
        }
        return null
    }


    private suspend fun analyzeFoodPandaHtmlStore(
        html: String?,
        context: Context,
        gpsPosition: List<Double>
    ): List<Store> {
        val document = Jsoup.parse(html).html(html)
        val liTag = document.select("li a figure")
        val list = ArrayList<Store>()
        withContext(Dispatchers.IO) {
            liTag.forEachIndexed { index, element ->
                if (index == 10) return@forEachIndexed
                val imgUrl = element.getElementsByClass("vendor-picture")[0].dataset()
                    .getValue("src")
                var img: Bitmap? = null
                if (index < 30) {
                    img = Glide.with(context).asBitmap().load(imgUrl).submit().get()
                }
                val random = Random.nextDouble(-0.03, 0.03)
                val storePosition = listOf(gpsPosition[0] + random, gpsPosition[1] + random)
                list.add(
                    Store(
                        position = index,
                        name = element.getElementsByClass("name fn")[0].text(),
                        imgUrl = imgUrl,
                        img = img,
                        menuUrl = element.parent().attr("href").split(Config.FoodPandaURL)[1],
                        gpsPosition = storePosition
                    )
                )
            }
        }
        return list
    }

//    suspend fun getStringOfMenuByStore(storeId: UUID, position:Int, storeName:String): LiveData<List<Menu>>? {
//        val res = foodPanda.getStringOfMenuByStore()
//        if(res.isSuccessful){
//
//        }
//        return null
//    }

//    private fun analyzeFoodPandaHtmlMenu(html:String?,storeId:Int,storeName:String): LiveData<List<Menu>> {
//        val document = Jsoup.parse(html).html(html)
//        val liTag = document.select("li.dish-card")
//        val list = ArrayList<Menu>()
//        val mlist = MutableLiveData<List<Menu>>()
//        Log.v("TAG_CHECK", "size ${liTag.size}")
//        liTag.forEachIndexed { index, element ->
//            val content = element.child(0).attr("aria-label").split(",  ")
//            val menuName = element.select("[data-testid]")?:content[1].split(" - ")[1]
////            val imgUrl = element.select("div.photo")?.attr("style")
//            Log.v("TAG_CHECK", "content ${menuName}")
////            Log.v("TAG_CHECK", "imgUrl ${imgUrl}")
////            list.add(
////                Menu(
////                    id = index,
////                    storeId=storeId,
////                    storeName = storeName,
////                    price= content[1].split(" - ")[1],
////                    menuName = content[0],
////                    imgUrl= imgUrl?.substring(23, imgUrl.length-3)
////                )
////            )
////            Log.v("TAG_CHECK","menu: ${list[index]}")
//        }
//        mlist.postValue(list)
//        return mlist
//    }
}