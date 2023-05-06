package thunderCore.commands.staffCommands

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import thunderCore.ThunderCore
import thunderCore.utilities.Messages

class GetVanishedCommand : CommandExecutor {
    private val vanished: ArrayList<Player> = VanishCommand.getVanished()
    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage(Messages.CONSOLECANTUSE)
            return false
        }
        if (!ThunderCore.get.isModerator(sender)) {
            sender.sendMessage(Messages.NOPERMS)
            return true
        }
        if (vanished.isEmpty()) {
            sender.sendMessage(Component.text("There are no vanished players!", NamedTextColor.RED))
            return true
        }
        val vanishedPlayers = StringBuilder()
        for (vPlayer in vanished) {
            vanishedPlayers.append(vPlayer.name).append(", ")
        }
        sender.sendMessage(Component.text(vanishedPlayers.toString() + "are vanished!", NamedTextColor.GREEN))
        return true
    }
}
