package thunderCore.managers.npcmanager

import com.mojang.authlib.GameProfile
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket
import net.minecraft.network.protocol.game.PacketPlayOutEntityDestroy
import net.minecraft.network.protocol.game.PacketPlayOutEntityHeadRotation
import net.minecraft.network.protocol.game.PacketPlayOutNamedEntitySpawn
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.EntityPlayer
import net.minecraft.server.network.PlayerConnection
import net.minecraft.world.entity.Entity
import org.bukkit.Bukkit
import org.bukkit.craftbukkit.v1_19_R3.CraftServer
import org.bukkit.craftbukkit.v1_19_R3.CraftWorld
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer
import org.bukkit.entity.Player
import thunderCore.managers.ThunderManager
import java.util.*


class NPCManager: ThunderManager {

    val npcs: HashMap<String, EntityPlayer> = HashMap()

    companion object {
        lateinit var get: NPCManager
    }
    init {
        get = this

    }

    fun createNPC(id: String, name: String, player: Player) {
        val customName = name.replace('~', '§', true)
        val entityPlayer: EntityPlayer = (player as CraftPlayer).handle
        val nmsServer: MinecraftServer = (Bukkit.getServer() as CraftServer).server
        // Change "world" to the world the NPC should be spawned in.
        val nmsWorld = entityPlayer.x()
        // Change "player name" to the name the NPC should have, max 16 characters.
        val gameProfile = GameProfile(UUID.randomUUID(), customName)
        val npc = EntityPlayer(nmsServer, nmsWorld, gameProfile) // This will be the EntityPlayer (NPC) we send with the sendNPCPacket method.
        val location = player.location
        //npc.teleportTo(nmsWorld, Position(location.x, location.y, location.z))
        npcs[id] = npc
        if (npcs.isNotEmpty()) {
            for (p in Bukkit.getOnlinePlayers()) {
                for (n in npcs.values) { addNPCPacket(n, p) }
            }
        }

    }

    fun removeNPCPacket(npc: Entity, player: Player) {
        val connection: PlayerConnection = (player as CraftPlayer).handle.b
        connection.a(PacketPlayOutEntityDestroy(npc.ak))
    }

    fun addNPCPacket(npc: EntityPlayer, player: Player) {
        val connection: PlayerConnection = (player as CraftPlayer).handle.b
        // "Adds the player data for the client to use when spawning a player" - https://wiki.vg/Protocol#Spawn_Player
        //connection.a(ClientboundPlayerInfoUpdatePacket())
        // Spawns the NPC for the player client.
        connection.a(PacketPlayOutNamedEntitySpawn(npc))
        // Correct head rotation when spawned in player look direction.
        connection.a(PacketPlayOutEntityHeadRotation(npc, Byte.MIN_VALUE))
    }

    fun getNPCByID(id: String): EntityPlayer? {
        for (npc in npcs.values) {
            if (npc.displayName == id) { return npc }
        }
        return null
    }
    fun getNPCByName(name: String): EntityPlayer? {
        for (npc in npcs.values) {
            if (npc.displayName == name) { return npc }
        }
        return null
    }


    override fun load() {
        //Load Npcs
    }

    override fun save() {
        //Save NPCs
    }
}
