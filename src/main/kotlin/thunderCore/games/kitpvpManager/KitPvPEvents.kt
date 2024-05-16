package thunderCore.games.kitpvpManager

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerChangedWorldEvent
import org.bukkit.scheduler.BukkitRunnable
import thunderCore.ThunderCore

class KitPvPEvents: Listener {

    private val spawn = Location(Bukkit.getWorld("kitpvp"), -6.5, 136.0, -54.5)

    @EventHandler
    fun worldChangeEvent(event: PlayerChangedWorldEvent) {
        if (event.from == Bukkit.getWorld("kitpvp")) {
            KitPvPManager.get.players.remove(event.player)
            event.player.inventory.clear()
            return
        }
    }

    @EventHandler
    fun playerAttackEvent(event: EntityDamageByEntityEvent) {
        if (event.entity.world != Bukkit.getWorld("kitpvp")) {
            return
        }
        if (event.entity.location.x > spawn.x - 20 && event.entity.location.x < spawn.x + 20 && event.entity.location.z > spawn.z - 20 && event.entity.location.z < spawn.z + 20) {
            event.isCancelled = true
            return
        }
        val player: Player = event.entity as Player
        if (player.health < 1) {
            val message: String = if (player.killer == null) {
                "" + ChatColor.RED + "${player.name} has died!"
            } else {
                "" + ChatColor.RED + "${player.name} was killed by ${player.killer!!.name}!"
            }
            for (players in Bukkit.getWorld("kitpvp")!!.players) {
                players.sendMessage(message)
            }
            object : BukkitRunnable() {
                override fun run() {
                    player.teleport(Location(Bukkit.getWorld("kitpvp"), 0.5, 72.0, 0.5))
                    return
                }
            }.runTaskLater(ThunderCore.get, 1L)
            event.isCancelled = true
        }
    }
}