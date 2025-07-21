package thunderCore.commands.staffCommands.worlds

import org.bukkit.ChatColor
import org.bukkit.WorldCreator
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import thunderCore.ThunderCore
import thunderCore.utilities.Messages

class LoadWorldCommand: CommandExecutor {

    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<String>): Boolean {
        if (sender is Player && !ThunderCore.get.isAdmin(sender)) {
            sender.sendMessage(Messages.NOPERMS)
            return true
        }

        if (args.isEmpty()) {
            sender.sendMessage("" + ChatColor.RED + "Please enter a world name to load!")
            return true
        }

        try {
            val worldCreator = WorldCreator(args[0])
            worldCreator.createWorld()
        } catch (e: Exception) {
            sender.sendMessage("" + ChatColor.RED + "That is not a valid world or there was an error loading!")
        }

        return true
    }
}