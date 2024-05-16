package thunderCore.commands.staffCommands

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import thunderCore.ThunderCore
import thunderCore.managers.playerManager.FakePlayer
import thunderCore.managers.playerManager.PlayerManager
import thunderCore.utilities.Messages

class SetRankCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender !is Player) {

            for (playerRank in PlayerManager.get.getPlayerRanks()) {

                if (args[1].equals(playerRank.name, ignoreCase = true)) {

                    if (Bukkit.getPlayer(args[0]) == null) {
                        sender.sendMessage(Messages.NOTAPLAYER)
                        return false
                    }

                    val fakePlayer: FakePlayer = Bukkit.getPlayer(args[0])?.let { PlayerManager.get.getFakePlayer(it) }!!
                    fakePlayer.rank = playerRank
                    return true
                }
            }
            sender.sendMessage("${ChatColor.RED}That is not an available rank!")
            return false
        }

        if (!ThunderCore.get.isAdmin(sender)) {
            sender.sendMessage(Messages.NOPERMS)
            return true
        }

        if (args.isEmpty()) {
            sender.sendMessage("${ChatColor.RED}You must provide a player and a rank!")
            return false
        }

        if (Bukkit.getPlayer(args[0]) == null) {
            sender.sendMessage(Messages.NOTAPLAYER)
            return false
        }

        val rankedPlayer: Player = Bukkit.getPlayer(args[0])!!
        if (args[1].isEmpty()) {
            sender.sendMessage("${ChatColor.RED}You must provide a rank!")
            return false
        }

        for (playerRank in PlayerManager.get.getPlayerRanks()) {

            if (args[1].equals(playerRank.name, ignoreCase = true)) {

                if (playerRank.permlevel >= PlayerManager.get.getPlayerRank(sender)!!.permlevel) {
                    sender.sendMessage("${ChatColor.RED}You cannot give a rank equal to or higher than yours!")
                    return true
                }

                val fakePlayer: FakePlayer = PlayerManager.get.getFakePlayer(rankedPlayer)!!
                fakePlayer.rank = playerRank
                sender.sendMessage("${ChatColor.GREEN}${rankedPlayer.name}'s rank has been set to ${playerRank.name}")
                rankedPlayer.sendMessage("${ChatColor.GREEN}Your rank has been set to ${playerRank.name}")
                return true
            }
        }
        sender.sendMessage("${ChatColor.RED}That is not an available rank!")
        return false
    }
}
