package thunderCore.events

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.CreatureSpawnEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerAttemptPickupItemEvent
import org.bukkit.event.player.PlayerItemDamageEvent
import thunderCore.commands.staffCommands.buildCommand.BuildManager

class WorldProtection : Listener {
    @EventHandler
    fun onBreak(event: BlockBreakEvent) {
        val player: Player = event.player
        val world: World = event.block.world
        for (s in protectedWorlds) {
            if (world == Bukkit.getWorld(s)) {
                if (!BuildManager.builders.contains(player)) {
                    player.sendMessage(ChatColor.RED.toString() + "You can't break that block!")
                    event.isCancelled = true
                }
            }
        }
    }

    @EventHandler
    fun onPlace(event: BlockPlaceEvent) {
        val player: Player = event.player
        val world: World = event.block.world
        for (s in protectedWorlds) {
            if (world == Bukkit.getWorld(s)) {
                if (!BuildManager.builders.contains(player)) {
                    event.isCancelled = true
                }
            }
        }
    }

    @EventHandler
    fun onItemDamage(event: PlayerItemDamageEvent) {
        val world: World = event.player.world
        for (s in protectedWorlds) {
            if (world == Bukkit.getWorld(s)) {
                event.isCancelled = true
            }
        }
    }

    @EventHandler
    fun entityDamageByEntity(event: EntityDamageByEntityEvent) {
        if (event.entity is Player && event.damager is Player) {
            if (event.damager.world === Bukkit.getWorld("lobby")) {
                event.isCancelled = true
            }
        }
    }

    @EventHandler
    fun pickUp(event: PlayerAttemptPickupItemEvent) {
        val world: World = event.player.world
        for (s in protectedWorlds) {
            if (world == Bukkit.getWorld(s)) {
                event.isCancelled = true
            }
        }
    }

    @EventHandler
    fun creatureSpawnEvent(event: CreatureSpawnEvent) {
        val world: World = event.entity.world
        for (s in protectedWorlds) {
            if (world == Bukkit.getWorld(s)) {
                event.isCancelled = true
            }
        }
    }

    @EventHandler
    fun damage(event: EntityDamageEvent) {
        val world: World = event.entity.world
        if (world === Bukkit.getWorld("lobby")) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onDeath(event: PlayerDeathEvent) {
        val player: Player = event.entity
        if (player.world.name == "lobby") {
            val location = Location(Bukkit.getWorld("lobby"), 0.5, 72.0, 0.5)
            player.teleport(location)
        }
    }

    companion object {
        private val protectedWorlds: List<String> =
            ArrayList(mutableListOf("world", "kitpvp", "pvp", "lobby", "dueltemplate"))
    }
}
