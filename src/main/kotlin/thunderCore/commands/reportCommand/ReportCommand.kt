package thunderCore.commands.reportCommand

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import thunderCore.managers.reportManager.ReportManager
import thunderCore.utilities.Messages
import java.util.*

class ReportCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage(Messages.CONSOLECANTUSE)
            return false
        }
        if (args[0].isEmpty()) {
            sender.sendMessage("${ChatColor.RED}You must provide a player to report and a reason!")
            return false
        }
        val toReport: Player? = Bukkit.getPlayer(args[0])
        if (toReport == null) {
            sender.sendMessage(Messages.NOTAPLAYER)
            return false
        }
        if (args[1].isEmpty()) {
            sender.sendMessage("${ChatColor.RED}You must provide a report reason!")
            return false
        }
        val reason = StringBuilder()
        for (str in args.copyOfRange(1, args.size)) {
            reason.append(str).append(" ")
        }
        ReportManager.get.createReport(sender, toReport, reason.toString(), UUID.randomUUID())
        sender.sendMessage("${ChatColor.GOLD}You have successfully reported ${ChatColor.RED}${toReport.name}")
        return true
    }
}
