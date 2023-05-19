package thunderCore.commands.staffCommands.bypassCommand

import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import thunderCore.ThunderCore
import thunderCore.utilities.Messages

class BypassCommand: CommandExecutor{
    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<String>?): Boolean {
        if (sender !is Player) {
            sender.sendMessage(Messages.CONSOLECANTUSE)
            return false;
        }
        val player = sender.player!!
        if (!(ThunderCore.get.isAdmin(player))) {
            player.sendMessage(Messages.NOPERMS)
            return true;
        }
        if (BypassManager.bypassing.contains(player)) {
            BypassManager.bypassing.remove(player)
            player.sendMessage("${ChatColor.GREEN}You are no longer bypassing World Protection!")
            return true;
        }
        BypassManager.bypassing.add(player)
        player.sendMessage("${ChatColor.RED}You are now bypassing World Protection!")
        return true;
    }


}