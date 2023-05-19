package thunderCore.events

import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent
import thunderCore.commands.staffCommands.buildCommand.BuildManager

class PlayerLeave : Listener {
    @EventHandler
    fun onLeave(event: PlayerQuitEvent) {
        val player: Player = event.player
        event.quitMessage = "" + ChatColor.RED + "${player.name} has left the server!"
        BuildManager.builders.remove(event.player)
    }
}
