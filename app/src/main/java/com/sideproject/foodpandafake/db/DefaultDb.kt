package com.sideproject.foodpandafake.db

import com.sideproject.foodpandafake.model.Menu
class DefaultDb {

    fun initMenu():List<Menu> {
            val list = mutableListOf<Menu>()
            list.add(
                Menu(
                    price = "201",
                    menuName = "小肥牛咖哩",
                    imgUrl = "https://images.deliveryhero.io/image/fd-tw/Products/45332091.jpg?width=200"
                )
            )
            list.add(
                Menu(
                    price = "185",
                    menuName = "豬肋排咖哩",
                    imgUrl = "https://images.deliveryhero.io/image/fd-tw/Products/45571971.jpg?width=200"
                )
            )
            list.add(
                Menu(
                    price = "165",
                    menuName = "舒肥雞咖哩飯",
                    imgUrl = "https://images.deliveryhero.io/image/fd-tw/Products/42557366.jpg?width=200"
                )
            )
            list.add(
                Menu(
                    price = "185",
                    menuName = "豚肉咖哩",
                    imgUrl = "https://images.deliveryhero.io/image/fd-tw/Products/45332093.jpg?width=200"
                )
            )

            list.add(
                Menu(
                    price = "160",
                    menuName = "廖家生炒花枝羹紅藜滷肉飯",
                    imgUrl = "https://images.deliveryhero.io/image/fd-tw/Products/20730999.jpg?width=200"
                )
            )
            list.add(
                Menu(
                    price = "169",
                    menuName = "海浪滔滔我不怕",
                    imgUrl = "https://images.deliveryhero.io/image/fd-tw/Products/20730996.jpg?width=200"
                )
            )
            list.add(
                Menu(
                    price = "178",
                    menuName = "主廚親炒花枝",
                    imgUrl = "https://images.deliveryhero.io/image/fd-tw/Products/20730998.jpg?width=200"
                )
            )
            list.add(
                Menu(
                    price = "160",
                    menuName = "廖家生炒花枝羹滷肉麵",
                    imgUrl = "https://images.deliveryhero.io/image/fd-tw/Products/20731001.jpg?width=200"
                )
            )

            list.add(
                Menu(
                    price = "360",
                    menuName = "OZ 火腿Double Cheese蛋",
                    imgUrl = "https://images.deliveryhero.io/image/fd-tw/Products/50825907.jpg?width=200"
                )
            )
            list.add(
                Menu(
                    price = "350",
                    menuName = "OZ 紅酒牛頰瑪斯卡彭",
                    imgUrl = "https://images.deliveryhero.io/image/fd-tw/Products/50825910.jpg?width=200"
                )
            )
            list.add(
                Menu(
                    price = "380",
                    menuName = "OZ 火腿布里乳酪",
                    imgUrl = "https://images.deliveryhero.io/image/fd-tw/Products/50825906.jpg?width=200"
                )
            )
            list.add(
                Menu(
                    price = "299",
                    menuName = "OZ 嫩烤雞胸酪梨",
                    imgUrl = "https://images.deliveryhero.io/image/fd-tw/Products/50825908.jpg?width=200"
                )
            )
            return list
    }
}