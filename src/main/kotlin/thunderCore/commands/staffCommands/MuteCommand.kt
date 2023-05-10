package thunderCore.commands.staffCommands

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
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
            sender.sendMessage(Component.text("You must provide a player to mute!", NamedTextColor.RED))
            return false
        }
        if (Bukkit.getPlayer(args[0]) == null) {
            sender.sendMessage(Messages.NOTAPLAYER)
            return false
        }
        val muted: Player = Bukkit.getPlayer(args[0])!!
        val mutedFakePlayer: FakePlayer = PlayerManager.getFakePlayer(muted)!!
        if (PlayerManager.getPlayerRank(sender)!!.permlevel <= PlayerManager.getPlayerRank(muted)!!.permlevel) {
            sender.sendMessage(Component.text("You cannot mute that player!", NamedTextColor.RED))
            return true
        }
        if (label == "mute") {
            if (mutedFakePlayer.muted) {
                sender.sendMessage(Component.text("${muted.name} is already muted", NamedTextColor.RED))
                return true
            }
            sender.sendMessage(Component.text("${muted.name} has been MUTED!", NamedTextColor.RED))
            mutedFakePlayer.muted = true
        } else {
            if (!mutedFakePlayer.muted) {
                sender.sendMessage(Component.text("${muted.name} isn't muted", NamedTextColor.RED))
                return true
            }
            sender.sendMessage(Component.text("${muted.name} has been UNMUTED!", NamedTextColor.RED))
            mutedFakePlayer.muted = false
        }
        return true
    }
}
