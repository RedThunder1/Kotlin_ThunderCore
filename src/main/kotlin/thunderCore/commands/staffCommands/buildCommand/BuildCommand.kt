package thunderCore.commands.staffCommands.buildCommand

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
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
        if (!(ThunderCore.get().isBuilder(sender) || ThunderCore.get().isAdmin(sender))) {
            sender.sendMessage(Messages.NOPERMS)
            return true
        }
        if (BuildManager.builders.contains(sender)) {
            BuildManager.builders.remove(sender)
            sender.sendMessage(Component.text("Build mode Disabled", NamedTextColor.RED))
            return true
        }
        BuildManager.builders.add(sender)
        sender.sendMessage(Component.text("Build mode Enabled", NamedTextColor.GREEN))
        return true
    }
}
