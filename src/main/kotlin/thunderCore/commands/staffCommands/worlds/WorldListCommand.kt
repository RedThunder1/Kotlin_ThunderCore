package thunderCore.commands.staffCommands.worlds

import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import thunderCore.ThunderCore
import thunderCore.utilities.Messages

class WorldListCommand: CommandExecutor {
    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<out String>?): Boolean {
        if (sender !is Player) {
            sender.sendMessage(Messages.CONSOLECANTUSE)
            return false
        }
        val player = sender.player!!
        if (!ThunderCore.get.isStaff(player)) {
            sender.sendMessage(Messages.NOPERMS)
            return false
        }
        player.sendMessage("${ChatColor.GOLD}_______Worlds_______")
        for (world in Bukkit.getWorlds()) {
            var msg = TextComponent("${ChatColor.GOLD}| ${world.name}")
            msg.clickEvent = ClickEvent(ClickEvent.Action.RUN_COMMAND, "/wtp ${world.name}")
            msg.hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, ComponentBuilder("" + ChatColor.GOLD + "Join world").create())
            if (player.world == world) {
                msg.addExtra("${ChatColor.GREEN} <-- Here")
            }
            player.spigot().sendMessage(msg)
        }
        return true
    }
}