package thunderCore.managers.npcmanager

import com.mojang.authlib.GameProfile
import net.minecraft.server.level.ServerLevel
import org.bukkit.Bukkit
import org.bukkit.craftbukkit.v1_19_R3.CraftServer
import org.bukkit.craftbukkit.v1_19_R3.CraftWorld
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftEntity
import org.bukkit.entity.Player
import thunderCore.managers.ThunderManager
import java.util.*


class NPCManager: ThunderManager {
    companion object {
        lateinit var get: NPCManager
    }
    init {
        get = this


    }

    fun createNPC(name: String, player: Player) {
        //Creates npc
    }


    override fun load() {
        //Load Npcs
    }

    override fun save() {
        //Save NPCs
    }
}
