package thunderCore.commands.staffCommands

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import thunderCore.ThunderCore
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import thunderCore.utilities.Messages

class VanishCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage(Messages.CONSOLECANTUSE)
            return false
        }
        if (!ThunderCore.get().isStaff(sender)) {
            sender.sendMessage(Messages.NOPERMS)
            return true
        }
        if (vanished.contains(sender)) {
            vanished.remove(sender)
            sender.showPlayer(ThunderCore.get(), sender)
            sender.sendMessage(Component.text("You have unvanished!", NamedTextColor.RED))
            return true
        }
        vanished.add(sender)
        sender.hidePlayer(ThunderCore.get(), sender)
        sender.sendMessage(Component.text("You have vanished!", NamedTextColor.GREEN))
        return true
    }

    companion object {
        private val vanished: ArrayList<Player> = ArrayList()
        @JvmStatic
        fun getVanished(): ArrayList<Player> {
            return vanished
        }
    }
}
