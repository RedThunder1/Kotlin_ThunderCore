package thunderCore.commands.staffCommands.buildCommand

import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import thunderCore.ThunderCore
import thunderCore.utilities.Messages

class BuildCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage(Messages.CONSOLECANTUSE)
            return false
        }
        if (!(ThunderCore.get.isBuilder(sender) || ThunderCore.get.isAdmin(sender))) {
            sender.sendMessage(Messages.NOPERMS)
            return true
        }
        if (BuildManager.builders.contains(sender)) {
            BuildManager.builders.remove(sender)
            sender.sendMessage("${ChatColor.RED}Build mode Disabled")
            return true
        }
        BuildManager.builders.add(sender)
        sender.sendMessage("${ChatColor.GREEN}Build mode Enabled")
        return true
    }
}
