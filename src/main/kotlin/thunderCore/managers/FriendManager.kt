package thunderCore.managers

import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import thunderCore.managers.playerManager.PlayerManager
import java.util.UUID

class FriendManager: ThunderManager {
    companion object {
        lateinit var get: FriendManager
    }
    init { get = this}
    private val requests = ArrayList<HashMap<UUID, UUID>>()

    fun requestFriend(player: Player, added: Player) {
        for (request in requests) {
            if (request.contains(player.uniqueId) && request.contains(added.uniqueId)) {
                player.sendMessage("" + ChatColor.RED + "You have already added ${added.name}!")
                return
            }
        }

        val accept = TextComponent("" + ChatColor.BOLD + ChatColor.GREEN + "ACCEPT")
        accept.clickEvent = ClickEvent(ClickEvent.Action.RUN_COMMAND, "friend accept ${player.name}")

        val deny = TextComponent("" + ChatColor.BOLD + ChatColor.RED + "DENY")
        deny.clickEvent = ClickEvent(ClickEvent.Action.RUN_COMMAND, "friend deny ${player.name}")

        player.sendMessage("" + ChatColor.GOLD + "You have requested to be friends with ${added.name}!")
        added.spigot().sendMessage(TextComponent("" + ChatColor.GOLD + "${player.name} has requested to your friend! " + accept + ChatColor.GOLD + " || " + deny))
        val map = HashMap<UUID, UUID>()
        map[player.uniqueId] = added.uniqueId
        requests.add(map)
    }

    fun acceptFriend(adder: Player, added: Player) {
        //check if there is a valid request
        val request = getRequestByPlayers(adder, added)
        if (request == null) {
            added.sendMessage("" + ChatColor.RED + "You have not gotten a request from ${adder.name}")
            return
        }
        PlayerManager.get.getFakePlayer(adder)!!.friends.add(added)
        adder.sendMessage("" + ChatColor.GREEN + "You are now friends with ${added.name}!")
        PlayerManager.get.getFakePlayer(added)!!.friends.add(adder)
        added.sendMessage("" + ChatColor.GREEN + "You are now friends with ${adder.name}!")
    }

    fun denyFriend(adder: Player, added: Player) {
        //check if there is a valid request
        val request = getRequestByPlayers(adder, added)
        if (request == null) {
            added.sendMessage("" + ChatColor.RED + "You have not gotten a request from ${adder.name}")
            return
        }
        adder.sendMessage("" + ChatColor.RED + "${added.name} denied your friend request!")
        added.sendMessage("" + ChatColor.RED + "You denied ${adder.name} friend request!")
    }

    fun removeFriend(player: Player, remove: Player) {
        if (PlayerManager.get.getFakePlayer(player)!!.friends.contains(remove)) {
            PlayerManager.get.getFakePlayer(player)!!.friends.remove(remove)
            PlayerManager.get.getFakePlayer(remove)!!.friends.remove(player)
            player.sendMessage("" + ChatColor.RED + "You are no longer friends with ${remove.name}!")
            remove.sendMessage("" + ChatColor.RED + "You are no longer friends with ${player.name}!")
        }
    }

    private fun getRequestByPlayers(adder: Player, added: Player): HashMap<UUID, UUID>? {
        for (request in requests) {
            if (request[adder.uniqueId] == added.uniqueId) { return request }
        }
        return null
    }
}