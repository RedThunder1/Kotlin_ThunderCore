package thunderCore.commands.kitpvp

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import thunderCore.games.kitpvpManager.KitPvPManager
import thunderCore.utilities.Messages

class KitPvpCommand: CommandExecutor {
    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage(Messages.CONSOLECANTUSE)
            return false
        }
        val player = sender.player!!
        if (player.world == Bukkit.getWorld("kitpvp")!!) {
            player.teleport(Location(Bukkit.getWorld("kitpvp"), 0.5, 72.0, 0.5))
            return true
        }
        KitPvPManager.get.playerJoin(player)
        return true
    }

}