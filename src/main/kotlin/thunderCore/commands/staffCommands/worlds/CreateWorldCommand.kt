package thunderCore.commands.staffCommands.worlds

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.World
import org.bukkit.WorldCreator
import org.bukkit.WorldType
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import thunderCore.ThunderCore
import thunderCore.utilities.Messages

class CreateWorldCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<String>): Boolean {
        if (sender !is Player) {
            createWorld(args, sender)
            return true
        }
        if (!(sender.isOp || ThunderCore.get().isAdmin(sender))) {
            sender.sendMessage(Messages.NOPERMS)
            return true
        }
        if (args.isEmpty()) {
            sender.sendMessage(Component.text("Please provide a world type like Normal, Flat, Void", NamedTextColor.RED))
            return false
        }
        createWorld(args, sender)
        return true
    }

    private fun createWorld(args: Array<String>, sender: CommandSender) {
        val name = args[0]
        val type = args[1].lowercase()
        val wc = WorldCreator(name)
        when (type) {
            "normal" -> {
                wc.environment(World.Environment.NORMAL)
                wc.type(WorldType.NORMAL)
                wc.createWorld()
            }

            "flat" -> {
                wc.environment(World.Environment.NORMAL)
                wc.type(WorldType.FLAT)
                wc.createWorld()
            }

            "void" -> {
                wc.generator(VoidWorldGenerator())
                wc.createWorld()
            }

            else -> {
                sender.sendMessage(Component.text("World types can only be normal, flat, or void!", NamedTextColor.RED))
                return
            }
        }
        sender.sendMessage(Component.text("You have created a new $type world named  ${name}!", NamedTextColor.GREEN))
    }
}
