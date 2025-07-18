package thunderCore.commands.reportCommand.viewReports

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import thunderCore.ThunderCore
import thunderCore.utilities.Messages

class ViewReportsCommand: CommandExecutor {
    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage(Messages.CONSOLECANTUSE)
            return true
        }

        if (!ThunderCore.get.isStaff(sender)) {
            sender.sendMessage(Messages.NOPERMS)
            return true
        }

        ViewReportsGUI.get.reportsGui(sender)

        return true
    }
}
