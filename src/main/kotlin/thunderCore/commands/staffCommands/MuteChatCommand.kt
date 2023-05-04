package thunderCore.commands.staffCommands

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
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
        if (!ThunderCore.get().isAdmin(sender)) {
            sender.sendMessage(Messages.NOPERMS)
            return true
        }
        if (ChatListener.chatMuted) {
            sender.sendMessage(Component.text("Chat has been unmuted!", NamedTextColor.GREEN))
            ChatListener.chatMuted = false
            return true
        }
        sender.sendMessage(Component.text("Chat has been muted!", NamedTextColor.RED))
        ChatListener.chatMuted = true
        return true
    }
}
