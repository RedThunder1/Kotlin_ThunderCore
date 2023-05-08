package thunderCore.events

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import thunderCore.managers.rankManager.RankManager

class PlayerJoin : Listener {
    private var lobbyWorld: World = Bukkit.getWorld("lobby")!!
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player: Player = event.player
        if (player.hasPlayedBefore()) {
            event.joinMessage(Component.text("${player.name} has joined the server!", NamedTextColor.AQUA))
        } else {
            event.joinMessage(Component.text("${player.name} has joined the server for the first time!", NamedTextColor.AQUA))
        }
        val location = Location(lobbyWorld, 0.5, 72.0, 0.5)
        player.teleport(location)
        player.health = 20.0
        player.saturation = 20f
        if (RankManager.get().getFakePlayer(player) == null) {
            RankManager.get().createFakePlayer(event.player, "member", null)
        }
        val rank = RankManager.get().getPlayerRank(player)
        val prefix = rank!!.prefix
        player.displayName(Component.text(prefix, RankManager.getRankColor(rank)).append(Component.text(player.name)))
        player.playerListName(Component.text(prefix, RankManager.getRankColor(rank)).append(Component.text(player.name)))
    }
}
