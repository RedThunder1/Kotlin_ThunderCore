package thunderCore.commands.staffCommands.worlds

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import thunderCore.ThunderCore
import thunderCore.utilities.Messages

class UnloadWorldCommand: CommandExecutor {

    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<String>): Boolean {
        if (sender is Player && !ThunderCore.get.isAdmin(sender)) {
            sender.sendMessage(Messages.NOPERMS)
            return true
        }

        if (args.isEmpty()) {
            sender.sendMessage("" + ChatColor.RED + "Please enter a world name to unload!")
            return true
        }

        try {
            Bukkit.unloadWorld(args[0], true)
        } catch (e: Exception) {
            sender.sendMessage("" + ChatColor.RED + "That is not a valid world or there was an error unloading!")
        }

        return true
    }

}