package thunderCore.commands.kitpvp.kitsCommand

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import thunderCore.utilities.Messages

class KitsCommand: CommandExecutor {
    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<String>?): Boolean {
        if (sender !is Player) {
            sender.sendMessage(Messages.CONSOLECANTUSE)
            return false;
        }
        val player = sender.player
        if (player!!.world != Bukkit.getWorld("kitpvp")) {
            player.sendMessage("${ChatColor.RED}You can only use this command in the kitpvp world!")
            return true;
        }
        /*
        if (player.location.x > -20 && player.location.x < 20 && player.location.z > -20 && player.location.z < 20) {
            player.sendMessage(Component.text("You must be in the spawn to change kits!", NamedTextColor.RED))
            return true
        }
         */
        val gui = KitsGUI()
        gui.kitsGui(player)
        return true;
    }

}