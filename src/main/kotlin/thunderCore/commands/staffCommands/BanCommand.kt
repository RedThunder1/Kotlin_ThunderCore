package thunderCore.commands.staffCommands

import org.apache.commons.lang3.StringUtils
import org.bukkit.BanList
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import thunderCore.ThunderCore
import thunderCore.managers.playerManager.PlayerManager
import thunderCore.utilities.Messages
import java.util.*

class BanCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage(Messages.CONSOLECANTUSE)
            return true
        }
        if (!ThunderCore.get.isModerator(sender)) {
            sender.sendMessage(Messages.NOPERMS)
            return true
        }
        if (args.isEmpty()) {
            sender.sendMessage("${ChatColor.RED}You must provide a player to ban, ban time, and a ban reason!")
            return false
        }
        if (args[1].isEmpty()) {
            sender.sendMessage("${ChatColor.RED}You must provide a ban time!")
            return false
        }
        if (args[2].isEmpty()) {
            sender.sendMessage("${ChatColor.RED}You must provide a ban reason!")
            return false
        }
        if (Bukkit.getPlayer(args[0]) == null) {
            sender.sendMessage(Messages.NOTAPLAYER)
            return false
        }
        val toBan: Player = Bukkit.getPlayer(args[0])!!
        if (PlayerManager.get.getPlayerRank(sender)!!.permlevel <= PlayerManager.get.getPlayerRank(toBan)!!.permlevel
        ) {
            sender.sendMessage("${ChatColor.RED}You cannot ban that player!")
            return true
        }
        val time = args[1]
        val reason = StringBuilder()
        for (str in args.copyOfRange(2, args.size)) {
            reason.append(str).append(" ")
        }
        var day = 0
        var hour = 0
        var minute = 0
        var end = 0
        for (i in time.indices) {
            if (time[i] == 'd') {
                if (i == 0) {
                    sender.sendMessage("${ChatColor.RED}Incorrect arguments! no amount of days provided!")
                    return false
                }
                try {
                    val check = time.substring(end, i - 1)
                    day = check.toInt()
                    end = i
                } catch (e: Exception) {
                    sender.sendMessage("${ChatColor.RED}Incorrect arguments! Incorrect day time provided!")
                    return false
                }
            } else if (time[i] == 'h') {
                if (i == 0) {
                    sender.sendMessage("${ChatColor.RED}Incorrect arguments! no amount of hours provided!")
                    return false
                }
                try {
                    val check = time.substring(end, i - 1)
                    hour = check.toInt()
                    end = i
                } catch (e: Exception) {
                    sender.sendMessage("${ChatColor.RED}Incorrect arguments! Incorrect hour time provided!")
                    return false
                }
            } else if (time[i] == 'm') {
                if (i == 0) {
                    sender.sendMessage("${ChatColor.RED}Incorrect arguments! no amount of minutes provided!")
                    return false
                }
                try {
                    val check = time.substring(end, i - 1)
                    minute = check.toInt()
                    end = i
                } catch (e: Exception) {
                    sender.sendMessage("${ChatColor.RED}Incorrect arguments! Incorrect minute time provided!")
                    return false
                }
            }
        }
        val utilMinute = 60 * 1000
        val utilHour = 60 * utilMinute
        val utilDay = 24 * utilHour
        if (day == 0) {
            day = 1
        } else {
            day *= utilDay
        }
        if (hour == 0) {
            hour = 1
        } else {
            hour *= utilHour
        }
        if (minute == 0) {
            minute = 1
        } else {
            minute *= utilMinute
        }
        val bumper = StringUtils.repeat("\n", 35)
        if (label == "ipban") {
            Bukkit.getBanList(BanList.Type.IP).addBan(
                toBan.name,
                bumper + reason.toString() + bumper,
                Date(System.currentTimeMillis() + day.toLong() * hour * minute),
                null
            )
            toBan.kickPlayer("${ChatColor.RED}$reason")
        } else {
            Bukkit.getBanList(BanList.Type.NAME).
            addBan(
                toBan.name,
                bumper + reason.toString() + bumper,
                Date(System.currentTimeMillis() + day.toLong() * hour * minute),
                null
            )
            toBan.kickPlayer("${ChatColor.RED}$reason")
        }
        return true
    }
}
