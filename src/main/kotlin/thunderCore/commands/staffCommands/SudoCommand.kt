package thunderCore.commands.staffCommands

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import thunderCore.managers.playerManager.PlayerManager
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
            sender.sendMessage("${ChatColor.RED}You must provide a player and message!")
            return false
        }
        if (Bukkit.getPlayer(args[0]) == null) {
            sender.sendMessage(Messages.NOTAPLAYER)
            return true
        }
        val p: Player = Bukkit.getPlayer(args[0])!!
        if (PlayerManager.get.getPlayerRank(sender)?.permlevel!! <= PlayerManager.get.getPlayerRank(p)?.permlevel!!) {
            sender.sendMessage("${ChatColor.RED}You cannot sudo that player!")
            return true
        }
        if (args[1].isBlank()) {
            sender.sendMessage("${ChatColor.RED}You must provide a message!")
            return false
        }
        val length = args.size
        val msg = args.contentToString().substring(p.name.length, length)
        if (args[1][0] == '/') {
            p.performCommand(msg)
            return true
        }
        for (members in Bukkit.getOnlinePlayers()) {
            //Change to do proper colors
            members.sendMessage("<" + p.name + "> " + msg)
        }
        return true
    }
}
