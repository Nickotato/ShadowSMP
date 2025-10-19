package me.nickotato.shadowSMP.manager

object ItemManager {
    data class CustomItemInfo (
        val type: String,
        val unburnable: Boolean = false,
        val unbreakable: Boolean = false,
    )

    private val items = mutableMapOf<String, CustomItemInfo>()

    fun register(item: CustomItemInfo) {
        items[item.type] = item
    }

    fun getInfo(type: String): CustomItemInfo? = items[type]

    fun isUnburnable(type: String): Boolean = items[type]?.unburnable == true
}