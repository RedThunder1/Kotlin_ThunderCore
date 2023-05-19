package thunderCore.events

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import thunderCore.managers.playerManager.PlayerManager

class PlayerJoin : Listener {
    private var lobbyWorld: World = Bukkit.getWorld("lobby")!!
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player: Player = event.player
        if (player.hasPlayedBefore()) {
            event.joinMessage = "" + ChatColor.AQUA + "${player.name} has joined the server!"
        } else {
            event.joinMessage = "" + ChatColor.AQUA + "${player.name} has joined the server for the first time!"
        }
        val location = Location(lobbyWorld, 0.5, 72.0, 0.5)
        player.teleport(location)
        player.health = 20.0
        if (PlayerManager.get.getFakePlayer(player) == null) {
            PlayerManager.get.createFakePlayer(event.player, "member", null)
        }
        val rank = PlayerManager.get.getPlayerRank(player)
        val prefix = rank!!.prefix
        player.setDisplayName("" + PlayerManager.get.getRankColor(rank) + prefix + player.name)
        player.setPlayerListName("" + PlayerManager.get.getRankColor(rank) + prefix + player.name)
        //Set player nametag here
    }
}
