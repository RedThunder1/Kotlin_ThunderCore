package thunderCore.commands.staffCommands.worlds

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.World
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import thunderCore.ThunderCore
import thunderCore.utilities.Messages
import java.util.*

class DeleteWorldCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender !is Player) {
            if (args.isEmpty()) {
                sender.sendMessage("${ChatColor.RED}Please provide a world to delete!")
                return false
            }
            val name = args[0]
            if (Bukkit.getWorld(name) == null) {
                sender.sendMessage("${ChatColor.RED}That is not a world!")
                return false
            }
            Bukkit.unloadWorld(name, false)
            if (Objects.requireNonNull<World>(Bukkit.getWorld(name)).worldFolder.delete()) {
                sender.sendMessage("${ChatColor.DARK_RED}The world $name was deleted!")
            }
            return false
        }
        if (!(sender.isOp || ThunderCore.get.isAdmin(sender))) {
            sender.sendMessage(Messages.NOPERMS)
            return false
        }
        if (args.isEmpty()) {
            sender.sendMessage("${ChatColor.RED}Please provide a world to delete!")
            return false
        }
        val name = args[0]
        if (Bukkit.getWorld(name) == null) {
            sender.sendMessage("${ChatColor.RED}That is not a world!")
            return true
        }
        Bukkit.unloadWorld(name, false)
        if (Objects.requireNonNull<World>(Bukkit.getWorld(name)).worldFolder.delete()) {
            sender.sendMessage("${ChatColor.DARK_RED}The world $name was deleted!")
        }
        return true
    }
}
