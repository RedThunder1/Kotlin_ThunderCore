package thunderCore.commands

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import thunderCore.ThunderCore
import thunderCore.utilities.Messages

class LobbyCommand : CommandExecutor {
    private var lobby: World = Bukkit.getWorld("lobby")!!
    private var spawn: Location = Location(lobby, 0.5, 72.0, 0.5)
    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<String>): Boolean {
        if (sender !is Player) {
            ThunderCore.console.sendMessage(Messages.CONSOLECANTUSE)
            return false
        }
        sender.teleport(spawn)
        return true
    }
}
