package thunderCore.commands.reportCommand

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
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
            sender.sendMessage(Component.text("You must provide a player to report and a reason!", NamedTextColor.RED))
            return false
        }
        val toReport: Player? = Bukkit.getPlayer(args[0])
        if (toReport == null) {
            sender.sendMessage(Messages.NOTAPLAYER)
            return false
        }
        if (args[1].isEmpty()) {
            sender.sendMessage(Component.text("You must provide a report reason!", NamedTextColor.RED))
            return false
        }
        val reason = StringBuilder()
        for (str in args.copyOfRange(1, args.size)) {
            reason.append(str).append(" ")
        }
        ReportManager.createReport(sender, toReport, reason.toString(), UUID.randomUUID())
        sender.sendMessage(Component.text("You have successfully reported ", NamedTextColor.GOLD).append(Component.text(toReport.name, NamedTextColor.RED)))
        return true
    }
}
