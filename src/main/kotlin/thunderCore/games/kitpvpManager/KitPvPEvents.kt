package thunderCore.games.kitpvpManager

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerChangedWorldEvent

class KitPvPEvents: Listener {

    @EventHandler
    fun worldChangeEvent(event: PlayerChangedWorldEvent) {
        if (event.from == Bukkit.getWorld("kitpvp")) {
            KitPvPManager.players.remove(event.player)
            event.player.inventory.clear()
            return
        }
    }

    @EventHandler
    fun playerDeathEvent(event: PlayerDeathEvent) {
        if (event.player.world == Bukkit.getWorld("kitpvp")) {
            val message: Component = if (event.player.killer == null) {
                Component.text("${event.player.name} has died!", NamedTextColor.RED)
            } else {
                Component.text("${event.player.name} was killed by ${event.player.killer?.name}!", NamedTextColor.RED)
            }
            for (players in Bukkit.getWorld("kitpvp")!!.players) {
                players.sendMessage(message)
            }
            event.player.teleport(Location(Bukkit.getWorld("kitpvp"), 0.5, 72.0, 0.5))
            event.isCancelled = true
        }
    }
}