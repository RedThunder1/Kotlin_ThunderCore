package thunderCore.events

import io.papermc.paper.event.player.AsyncChatEvent
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import thunderCore.ThunderCore
import thunderCore.managers.rankManager.RankManager
import thunderCore.managers.rankManager.RankManager.getFakePlayer

class ChatListener : Listener {
    @EventHandler
    fun onMessage(event: AsyncChatEvent) {
        val player = event.player
        val message = event.originalMessage()
        val fakePlayer = getFakePlayer(player)!!
        if (chatMuted && !ThunderCore.get().isModerator(player)) {
            player.sendMessage(Component.text("The chat is currently muted!", NamedTextColor.RED))
            event.isCancelled = true
            return
        }
        if (fakePlayer.muted) {
            event.isCancelled = true
            player.sendMessage(Component.text("You are muted! You cannot send messages at this time!", NamedTextColor.RED))
            return
        }
        val plainSerializer = PlainTextComponentSerializer.plainText()
        val messageStr = plainSerializer.serialize(message)
        if (messageStr[0] == '#' && ThunderCore.get().isStaff(player)) {
            val msg1 = messageStr.substring(1)
            event.isCancelled = true
            for (staff in Bukkit.getOnlinePlayers()) {
                if (ThunderCore.get().isStaff(staff)) {
                    staff.sendMessage(
                        ChatColor.RED.toString() + "[STAFF CHAT] " + ChatColor.RESET + RankManager.get()
                            .getPlayerRank(player)!!.prefix + player.name + ChatColor.GOLD + msg1
                    )
                    return
                }
            }
        }
        for (players in Bukkit.getOnlinePlayers()) {
            if (players.world == player.world) {
                players.sendMessage(
                    Component.text(
                        RankManager.get()
                            .getPlayerRank(player)!!.prefix.toString() + player.name + ": " + ChatColor.RESET
                    ).append(event.originalMessage())
                )
            }
        }
        event.isCancelled = true
    }

    companion object {
        var chatMuted = false
    }
}