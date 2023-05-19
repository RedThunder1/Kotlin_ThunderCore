package thunderCore.commands.staffCommands.worlds

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
            val msg = "${ChatColor.GOLD}| ${world.name}"
            if (player.world == world) {
                msg + "${ChatColor.GREEN} <-- Here"
            }
            player.sendMessage(msg)
        }
        return true
    }
}