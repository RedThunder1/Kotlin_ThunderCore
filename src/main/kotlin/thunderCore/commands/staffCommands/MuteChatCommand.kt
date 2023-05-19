package thunderCore.commands.staffCommands

import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import thunderCore.ThunderCore
import thunderCore.events.ChatListener
import thunderCore.utilities.Messages

class MuteChatCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage(Messages.CONSOLECANTUSE)
            return false
        }
        if (!ThunderCore.get.isAdmin(sender)) {
            sender.sendMessage(Messages.NOPERMS)
            return true
        }
        if (ChatListener.chatMuted) {
            sender.sendMessage("${ChatColor.GREEN}Chat has been unmuted!")
            ChatListener.chatMuted = false
            return true
        }
        sender.sendMessage("${ChatColor.RED}Chat has been muted!")
        ChatListener.chatMuted = true
        return true
    }
}
