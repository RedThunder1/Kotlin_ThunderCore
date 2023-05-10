package thunderCore.commands.kitpvp

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
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
            player.sendMessage(Component.text("You are already in the kitpvp world! use /kits to open the kit menu!", NamedTextColor.RED))
            return true
        }
        KitPvPManager.playerJoin(player)
        return true
    }

}