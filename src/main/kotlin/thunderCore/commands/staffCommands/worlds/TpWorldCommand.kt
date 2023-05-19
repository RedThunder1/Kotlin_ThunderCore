package thunderCore.commands.staffCommands.worlds

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import thunderCore.ThunderCore
import thunderCore.utilities.Messages

class TpWorldCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage(Messages.CONSOLECANTUSE)
            return false
        }
        if (!(sender.isOp || ThunderCore.get.isStaff(sender))) {
            sender.sendMessage(Messages.NOPERMS)
            return false
        }
        if (args.isEmpty()) {
            sender.sendMessage("${ChatColor.RED}Please provide a world to teleport to!")
            return false
        }
        val w = args[0]
        if (Bukkit.getWorld(w) == null) {
            sender.sendMessage("${ChatColor.RED}That is not an available world!")
            return false
        }
        if (args.size > 1) {
            val p = args[1]
            if (Bukkit.getPlayer(p) != null) {
                val player1: Player = Bukkit.getPlayer(p)!!
                val loc = Location(Bukkit.getWorld(w), 0.5, 72.0, 0.5)
                player1.teleport(loc)
                return false
            } else {
                sender.sendMessage("${ChatColor.RED}That is not an online player!")
            }
            return false
        }
        val loc = Location(Bukkit.getWorld(w), 0.5, 72.0, 0.5)
        sender.teleport(loc)
        return true
    }
}
