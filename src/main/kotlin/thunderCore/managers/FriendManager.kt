package thunderCore.managers

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.entity.Player
import thunderCore.managers.playerManager.PlayerManager
import java.util.UUID

object FriendManager {
    private val requests = ArrayList<HashMap<UUID, UUID>>()

    fun requestFriend(player: Player, added: Player) {
        for (request in requests) {
            if (request.contains(player.uniqueId) && request.contains(added.uniqueId)) {
                player.sendMessage(Component.text("You have already added ${added.name}!", NamedTextColor.RED))
                return
            }
        }

        val accept = Component.text().build()
            .content("ACCEPT")
            .color(NamedTextColor.GREEN)
            .decorate(TextDecoration.BOLD)
            .clickEvent(ClickEvent.runCommand("friend accept ${player.name}"))
        val deny = Component.text().build()
            .content("DENY")
            .color(NamedTextColor.RED)
            .decorate(TextDecoration.BOLD)
            .clickEvent(ClickEvent.runCommand("friend deny ${player.name}"))

        player.sendMessage(Component.text("You have requested to be friends with ${added.name}! ", NamedTextColor.GREEN))
        added.sendMessage(Component.text().build()
            .content("${player.name} has requested to your friend!")
            .color(NamedTextColor.GOLD)
            .append(accept)
            .append(Component.text(" || ", NamedTextColor.GOLD))
            .append(deny))
        val map = HashMap<UUID, UUID>()
        map[player.uniqueId] = added.uniqueId
        requests.add(map)
    }

    fun acceptFriend(adder: Player, added: Player) {
        //check if there is a valid request
        val request = getRequestByPlayers(adder, added)
        if (request == null) {
            added.sendMessage(Component.text("You have not gotten a request from ${adder.name}"))
            return
        }
        PlayerManager.getFakePlayer(adder)!!.friends.add(added)
        adder.sendMessage(Component.text("You are now friends with ${added.name}!", NamedTextColor.GREEN))
        PlayerManager.getFakePlayer(added)!!.friends.add(adder)
        added.sendMessage(Component.text("You are now friends with ${adder.name}!", NamedTextColor.GREEN))
    }

    fun denyFriend(adder: Player, added: Player) {
        //check if there is a valid request
        val request = getRequestByPlayers(adder, added)
        if (request == null) {
            added.sendMessage(Component.text("You have not gotten a request from ${adder.name}"))
            return
        }
        adder.sendMessage(Component.text("${added.name} denied your friend request!", NamedTextColor.RED))
        added.sendMessage(Component.text("You denied ${adder.name} friend request!", NamedTextColor.RED))
    }

    fun removeFriend(player: Player, remove: Player) {
        if (PlayerManager.getFakePlayer(player)!!.friends.contains(remove)) {
            PlayerManager.getFakePlayer(player)!!.friends.remove(remove)
            PlayerManager.getFakePlayer(remove)!!.friends.remove(player)
            player.sendMessage(Component.text("You are no longer friends with ${remove.name}!", NamedTextColor.RED))
            remove.sendMessage(Component.text("You are no longer friends with ${player.name}!", NamedTextColor.RED))
        }
    }

    private fun getRequestByPlayers(adder: Player, added: Player): HashMap<UUID, UUID>? {
        for (request in requests) {
            if (request[adder.uniqueId] == added.uniqueId) { return request }
        }
        return null
    }
}