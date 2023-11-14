package com.example.shoppinglist.domain

class getShopItemUseCase(private val shopListRepository: ShopListRepository) {
    fun getShopItem(shopItemId : Int): ShopItem {
        return shopListRepository.getShopItem(shopItemId)
    }
}