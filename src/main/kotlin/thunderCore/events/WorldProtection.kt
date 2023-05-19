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
import org.bukkit.event.player.PlayerItemDamageEvent
import org.bukkit.event.player.PlayerPickupItemEvent
import org.bukkit.scheduler.BukkitRunnable
import thunderCore.ThunderCore
import thunderCore.commands.staffCommands.buildCommand.BuildManager
import thunderCore.commands.staffCommands.bypassCommand.BypassManager


class WorldProtection : Listener {
    @EventHandler
    fun onBreak(event: BlockBreakEvent) {
        val player: Player = event.player
        val world: World = event.block.world
        if (isWorldProtected(world)) {
            if (!(BuildManager.builders.contains(player) || BypassManager.bypassing.contains(player))) {
                player.sendMessage("" + ChatColor.RED + "You can't break that block!")
                event.isCancelled = true
            }
        }
    }

    @EventHandler
    fun onPlace(event: BlockPlaceEvent) {
        val player: Player = event.player
        val world: World = event.block.world
        if (!isWorldProtected(world)) {
            return
        }
        if (!(BuildManager.builders.contains(player) || BypassManager.bypassing.contains(player))) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onItemDamage(event: PlayerItemDamageEvent) {
        val world: World = event.player.world
        if (isWorldProtected(world)) {
            event.isCancelled = true
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
    fun pickUp(event: PlayerPickupItemEvent) {
        val world: World = event.player.world
        if (isWorldProtected(world)) {
            if (!(BypassManager.bypassing.contains(event.player))) {
                event.isCancelled = true
            }
        }
    }

    @EventHandler
    fun creatureSpawnEvent(event: CreatureSpawnEvent) {
        val world: World = event.entity.world
        if (isWorldProtected(world)) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun damage(event: EntityDamageEvent) {
        val world: World = event.entity.world
        if (!isWorldProtected(world)) {
            return
        }
        if (event.cause == EntityDamageEvent.DamageCause.VOID) {
            object : BukkitRunnable() {
                override fun run() {
                    if (event.entity is Player) {
                        event.entity.teleport(Location(event.entity.world, 0.5, 72.1, 0.5))
                        event.entity.fallDistance = 0f
                        event.isCancelled = true
                        return
                    }
                }
            }.runTaskLater(ThunderCore.get, 1L)
            if (event.entity is Player) {
                event.entity.teleport(Location(event.entity.world, 0.5, 72.1, 0.5))
                event.isCancelled = true
                return
            }
        }
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

    private fun isWorldProtected(world: World): Boolean {
        for (worlds in protectedWorlds) {
            if (world == Bukkit.getWorld(worlds)) {
                return true
            }
        }
        return false
    }

    companion object {
        private val protectedWorlds: List<String> =
            ArrayList(listOf("world", "kitpvp", "pvp", "lobby", "dueltemplate"))
    }
}
