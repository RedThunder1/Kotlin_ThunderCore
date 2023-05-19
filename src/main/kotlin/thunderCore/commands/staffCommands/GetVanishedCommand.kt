package thunderCore.commands.staffCommands

import org.bukkit.ChatColor
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
            sender.sendMessage("${ChatColor.RED}There are no vanished players!")
            return true
        }
        val vanishedPlayers = StringBuilder()
        for (vPlayer in vanished) {
            vanishedPlayers.append(vPlayer.name).append(", ")
        }
        sender.sendMessage("${ChatColor.GREEN}$vanishedPlayers are vanished!")
        return true
    }
}
