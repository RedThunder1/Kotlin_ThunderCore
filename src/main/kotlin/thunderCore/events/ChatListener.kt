package thunderCore.events

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import thunderCore.ThunderCore
import thunderCore.managers.playerManager.PlayerManager

class ChatListener : Listener {
    @EventHandler
    fun onMessage(event: AsyncPlayerChatEvent) {
        val player = event.player
        val message = event.message
        val fakePlayer = PlayerManager.get.getFakePlayer(player)!!
        if (chatMuted && !ThunderCore.get.isModerator(player)) {
            player.sendMessage("" + ChatColor.RED + "The chat is currently muted!")
            event.isCancelled = true
            return
        }
        if (fakePlayer.muted) {
            event.isCancelled = true
            player.sendMessage("" + ChatColor.RED + "You are muted! You cannot send messages at this time!")
            return
        }

        if (message.toCharArray()[0] == '#' && ThunderCore.get.isStaff(player)) {
            val msg1 = message.substring(1)
            event.isCancelled = true
            for (staff in Bukkit.getOnlinePlayers()) {
                if (ThunderCore.get.isStaff(staff)) {
                    staff.sendMessage("" + ChatColor.BOLD + ChatColor.RED + "[STAFF CHAT] " + PlayerManager.get.getPlayerRank(player)!!.prefix + player.name + ChatColor.GOLD + ": $msg1")
                    return
                }
            }
        }
        for (players in Bukkit.getOnlinePlayers()) {
            if (players.world == player.world) {
                val rank = PlayerManager.get.getPlayerRank(player)
                val prefix = rank!!.prefix + "${player.name}: "
                val color = PlayerManager.get.getRankColor(rank)
                players.sendMessage("" + color + prefix + ChatColor.WHITE + message)
            }
        }
        event.isCancelled = true
    }

    companion object {
        var chatMuted = false
    }
}
