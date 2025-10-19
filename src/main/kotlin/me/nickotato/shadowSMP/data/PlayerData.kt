package me.nickotato.shadowSMP.data

import me.nickotato.shadowSMP.enums.Charm
import me.nickotato.shadowSMP.enums.Ghost
import java.util.UUID

data class PlayerData(
    val uuid: UUID,
    var name: String,
    var souls: Int,
    var isUpgraded: Boolean,
    var ghost: Ghost,
    var charm: Charm?
)