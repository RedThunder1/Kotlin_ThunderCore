package thunderCore.events

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent
import thunderCore.commands.staffCommands.buildCommand.BuildManager

class PlayerLeave : Listener {
    @EventHandler
    fun onLeave(event: PlayerQuitEvent) {
        val player: Player = event.player
        event.quitMessage(Component.text("${player.name} has left the server!", NamedTextColor.RED))
        BuildManager.builders.remove(event.player)
    }
}
