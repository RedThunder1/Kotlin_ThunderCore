package thunderCore.commands.staffCommands.worlds

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import thunderCore.ThunderCore
import thunderCore.utilities.Messages

class TpWorldCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage(Messages.CONSOLECANTUSE)
            return false
        }
        if (!(sender.isOp || ThunderCore.get().isStaff(sender))) {
            sender.sendMessage(Messages.NOPERMS)
            return false
        }
        if (args.isEmpty()) {
            sender.sendMessage(Component.text("Please provide a world to teleport to!", NamedTextColor.RED))
            return false
        }
        val w = args[0]
        if (Bukkit.getWorld(w) == null) {
            sender.sendMessage(Component.text("That is not an available world!", NamedTextColor.RED))
            return false
        }
        if (args.size > 1) {
            val p = args[1]
            if (Bukkit.getPlayer(p) != null) {
                val world: World = Bukkit.getWorld(w)!!
                val player1: Player = Bukkit.getPlayer(p)!!
                val loc = world.spawnLocation
                player1.teleport(loc)
                return false
            } else {
                sender.sendMessage(Component.text("That is not an online player!", NamedTextColor.RED))
            }
            return false
        }
        val world: World = Bukkit.getWorld(w)!!
        val loc = world.spawnLocation
        sender.teleport(loc)
        return true
    }
}
