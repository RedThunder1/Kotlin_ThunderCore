package thunderCore.commands.staffCommands

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import thunderCore.managers.rankManager.RankManager
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import thunderCore.ThunderCore
import thunderCore.utilities.Messages

class SudoCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage(Messages.CONSOLECANTUSE)
            return false
        }
        if (!ThunderCore.get.isAdmin(sender)) {
            sender.sendMessage(Messages.NOPERMS)
            return true
        }
        if (args[0].isBlank()) {
            sender.sendMessage(Component.text("You must provide a player and message!", NamedTextColor.RED))
            return false
        }
        if (Bukkit.getPlayer(args[0]) == null) {
            sender.sendMessage(Messages.NOTAPLAYER)
            return true
        }
        val p: Player = Bukkit.getPlayer(args[0])!!
        if (RankManager.getPlayerRank(sender)?.permlevel!! <= RankManager.get().getPlayerRank(p)?.permlevel!!) {
            sender.sendMessage(Component.text("You cannot sudo that player!", NamedTextColor.RED))
            return true
        }
        val pLenght: Int = p.name.length
        if (args[1].isBlank()) {
            sender.sendMessage(Component.text("You must provide a message!", NamedTextColor.RED))
            return false
        }
        val length = args.size
        val msg = args.contentToString().substring(pLenght, length)
        if (args[1][0] == '/') {
            p.performCommand(msg)
            return true
        }
        for (members in Bukkit.getOnlinePlayers()) {
            members.sendMessage("<" + p.name + "> " + msg)
        }
        return true
    }
}
