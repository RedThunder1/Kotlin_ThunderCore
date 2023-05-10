package thunderCore.commands.kitpvp.kitsCommand

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
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
            player.sendMessage(Component.text("You can only use this command in the kitpvp world!", NamedTextColor.RED))
            return true;
        }
        KitsGUI.kitsGui(player)
        return true;
    }

}