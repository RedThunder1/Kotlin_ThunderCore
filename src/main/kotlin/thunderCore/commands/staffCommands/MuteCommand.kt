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

class MuteCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage(Messages.CONSOLECANTUSE)
            return false
        }
        if (!ThunderCore.get.isModerator(sender)) {
            sender.sendMessage(Messages.NOPERMS)
            return true
        }
        if (args[0].isEmpty()) {
            sender.sendMessage("${ChatColor.RED}You must provide a player to mute!")
            return false
        }
        if (Bukkit.getPlayer(args[0]) == null) {
            sender.sendMessage(Messages.NOTAPLAYER)
            return false
        }
        val muted: Player = Bukkit.getPlayer(args[0])!!
        val mutedFakePlayer: FakePlayer = PlayerManager.get.getFakePlayer(muted)!!
        if (PlayerManager.get.getPlayerRank(sender)!!.permlevel <= PlayerManager.get.getPlayerRank(muted)!!.permlevel) {
            sender.sendMessage("${ChatColor.RED}You cannot mute that player!")
            return true
        }
        if (label == "mute") {
            if (mutedFakePlayer.muted) {
                sender.sendMessage("${ChatColor.RED}${muted.name} is already muted")
                return true
            }
            sender.sendMessage("${ChatColor.RED}${muted.name} has been MUTED!")
            mutedFakePlayer.muted = true
        } else {
            if (!mutedFakePlayer.muted) {
                sender.sendMessage("${ChatColor.RED}${muted.name} isn't muted")
                return true
            }
            sender.sendMessage("${ChatColor.RED}${muted.name} has been UNMUTED!")
            mutedFakePlayer.muted = false
        }
        return true
    }
}
