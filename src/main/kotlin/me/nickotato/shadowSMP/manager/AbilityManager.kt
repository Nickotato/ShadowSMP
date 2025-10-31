package me.nickotato.shadowSMP.manager

import java.util.UUID

object AbilityManager {
    val invulnerablePlayers  = mutableSetOf<UUID>()
    val trueDamagePlayers = mutableSetOf<UUID>()
}