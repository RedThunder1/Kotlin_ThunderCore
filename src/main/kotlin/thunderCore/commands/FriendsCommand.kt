package thunderCore.commands

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import thunderCore.managers.playerManager.PlayerManager
import thunderCore.utilities.Messages

class FriendsCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<out String>?): Boolean {
        if (sender !is Player) {
            sender.sendMessage(Messages.CONSOLECANTUSE)
            return false
        }
        val player = sender.player!!
        if (args == null || args.isEmpty()) {
            player.sendMessage(Component.text("You must provide a command! Use /friends help for more information!", NamedTextColor.RED))
            return false
        }
        when (args[0].lowercase()) {
            "add" -> {
                if (args[1].isEmpty()) {
                    player.sendMessage(Component.text("You must provide a player to add!", NamedTextColor.RED))
                    return false
                }
                val added =  Bukkit.getPlayer(args[1])
                if (added == null) {
                    player.sendMessage(Messages.NOTAPLAYER)
                    return false
                }
            }
            "accept" -> {

            }
            "deny" -> {

            }
            "remove" -> {

            }
            "list" -> {
                val fakePlayer = PlayerManager.getFakePlayer(player)!!
                if (fakePlayer.friends.isEmpty()) {
                    player.sendMessage(Component.text("You don't have any friends yet!  You can add the by running the command /friend add <player>", NamedTextColor.RED))
                    return true
                }
                //Eventually I want to have this formatted into pages but for now this will work
                for (friend in fakePlayer.friends) {
                    val online: Component = if (friend.isOnline) {
                        Component.text("ONLINE", NamedTextColor.GREEN)
                    } else {
                        Component.text("OFFLINE", NamedTextColor.RED)
                    }
                    player.sendMessage(Component.text(friend.name + ": ", NamedTextColor.GOLD).append(online))
                }
            }
            "help"-> {
                sender.sendMessage(Component.text("The available commands for Friend are:\n" +
                        "/friend add <player> \n" +
                        "/friend remove <player> \n" +
                        "/friend accept <player> \n" +
                        "/friend deny <player>\n" +
                        "/friend remove <player>\n" +
                        "/friend list",
                    NamedTextColor.GOLD))
            }
            else -> {
                player.sendMessage(Component.text("That is not an avalible command! Use /friends help for more information!", NamedTextColor.RED))
            }
        }
        return true
    }
}