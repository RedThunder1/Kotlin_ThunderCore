package thunderCore.commands.staffCommands

import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import thunderCore.ThunderCore
import thunderCore.utilities.Messages
import java.lang.Exception

class FlySpeedCommand: CommandExecutor{
    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<out String>?): Boolean {
        if (sender !is Player) {
            sender.sendMessage(Messages.CONSOLECANTUSE)
            return false
        }
        val player = sender.player!!
        if (!ThunderCore.get.isStaff(player)) {
            player.sendMessage(Messages.NOPERMS)
            return true
        }
        if (args == null || args.isEmpty()) {
            player.sendMessage("${ChatColor.GREEN}Speed set to normal!")
            player.flySpeed = 0.1f
            return true
        }
        var speed: Float
        try {
            speed = args[0].toFloat()
        } catch (e: Exception) {
            player.sendMessage("${ChatColor.RED}That is not an available argument type, it must be a float between -1 and 1!")
            return false
        }
        if (speed > 1f) { speed = 1f } else if (speed < -1f) { speed = -1f }

        player.flySpeed = speed
        return true
    }
}