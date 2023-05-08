package thunderCore.games.kitpvp.kitpvpManager

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerChangedWorldEvent

class KitPvPEvents: Listener {

    @EventHandler
    fun worldChangeEvent(event: PlayerChangedWorldEvent) {
        if (event.from == Bukkit.getWorld("kitpvp")) {
            KitPvPManager.removePlayer(event.player)
            return
        }
    }

    @EventHandler
    fun playerDeathEvent(event: PlayerDeathEvent) {
        if (event.player.world == Bukkit.getWorld("kitpvp")) {
            for (players in Bukkit.getWorld("kitpvp")!!.players) {
                players.sendMessage(Component.text("${event.player.name} was killed by ${event.player.killer?.name}"))
            }
        }
    }
}