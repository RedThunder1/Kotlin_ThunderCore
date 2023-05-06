package thunderCore.commands.staffCommands

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import thunderCore.ThunderCore
import thunderCore.managers.rankManager.FakePlayer
import thunderCore.managers.rankManager.RankManager
import thunderCore.utilities.Messages

class SetRankCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender !is Player) {
            for (playerRank in RankManager.getPlayerRanks()) {
                if (args[1].equals(playerRank.name, ignoreCase = true)) {
                    if (Bukkit.getPlayer(args[0]) == null) {
                        sender.sendMessage(Messages.NOTAPLAYER)
                        return false
                    }
                    val fakePlayer: FakePlayer = Bukkit.getPlayer(args[0])?.let { RankManager.getFakePlayer(it) }!!
                    fakePlayer.rank = playerRank
                    return true
                }
            }
            sender.sendMessage(Component.text("That is not an available rank!", NamedTextColor.RED))
            return false
        }
        if (!ThunderCore.get.isOwner(sender)) {
            sender.sendMessage(Messages.NOPERMS)
            return true
        }
        if (args.isEmpty()) {
            sender.sendMessage(Component.text("You must provide a player and a rank!", NamedTextColor.RED))
            return false
        }
        if (Bukkit.getPlayer(args[0]) == null) {
            sender.sendMessage(Messages.NOTAPLAYER)
            return false
        }
        val rankedPlayer: Player = Bukkit.getPlayer(args[0])!!
        if (args[1].isEmpty()) {
            sender.sendMessage(Component.text("You must provide a rank!", NamedTextColor.RED))
            return false
        }
        for (playerRank in RankManager.get().getPlayerRanks()) {
            if (args[1].equals(playerRank.name, ignoreCase = true)) {
                val fakePlayer: FakePlayer = RankManager.getFakePlayer(rankedPlayer)!!
                fakePlayer.rank = playerRank
                return true
            }
        }
        sender.sendMessage(Component.text("That is not an available rank!", NamedTextColor.RED))
        return false
    }
}
