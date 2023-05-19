package thunderCore.utilities

import net.md_5.bungee.api.chat.*
import org.bukkit.ChatColor
import thunderCore.ThunderCore


class AnnouncementMessages: Runnable{

    private val games = "" + ChatColor.GOLD + "The current planned game is Bed Wars.  Once finished, other games will be developed."

    private val rules = "" + ChatColor.RED + "Please make sure to read our rules in the discord server!"

    private val welcome = "" + ChatColor.GREEN + "Welcome to ThunderMC! Here we will have many mini games such as Bed Wars, Sky Wars, and others soon to come!"

    private val discord = TextComponent("Join our discord by clicking here!");

    init {
        discord.clickEvent = ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/ueebY62cfw")
        discord.hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, ComponentBuilder("" + ChatColor.BLUE + "Join our discord!").create())
    }


    private var messageNumber = 0
    override fun run() {
        messageNumber++
        when (messageNumber) {
            1 -> ThunderCore.get.server.spigot().broadcast(discord)
            2 -> ThunderCore.get.server.broadcastMessage(rules)
            3 -> ThunderCore.get.server.broadcastMessage(games)
            4 -> {
                ThunderCore.get.server.broadcastMessage(welcome)
                messageNumber = 0
            }
            else -> messageNumber = 0
        }
    }
}