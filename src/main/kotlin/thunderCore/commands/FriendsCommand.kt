package thunderCore.commands

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import thunderCore.managers.FriendManager
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
            player.sendMessage("${ChatColor.RED}You must provide a command! Use /friends help for more information!")
            return false
        }
        when (args[0].lowercase()) {
            "add" -> {
                if (args[1].isEmpty()) {
                    player.sendMessage("${ChatColor.RED}You must provide a player to add!")
                    return false
                }
                val added =  Bukkit.getPlayer(args[1])
                if (added == null) {
                    player.sendMessage(Messages.NOTAPLAYER)
                    return false
                }
                FriendManager.get.requestFriend(player, added)
            }
            "accept" -> {
                if (args[1].isEmpty()) {
                    player.sendMessage("${ChatColor.RED}You must provide a player to add!")
                    return false
                }
                val added =  Bukkit.getPlayer(args[1])
                if (added == null) {
                    player.sendMessage(Messages.NOTAPLAYER)
                    return false
                }
                FriendManager.get.acceptFriend(added, player)
            }
            "deny" -> {
                if (args[1].isEmpty()) {
                    player.sendMessage("${ChatColor.RED}You must provide a player to add!")
                    return false
                }
                val added =  Bukkit.getPlayer(args[1])
                if (added == null) {
                    player.sendMessage(Messages.NOTAPLAYER)
                    return false
                }
                FriendManager.get.denyFriend(added, player)
            }
            "remove" -> {
                if (args[1].isEmpty()) {
                    player.sendMessage("${ChatColor.RED}You must provide a player to add!")
                    return false
                }
                val removed =  Bukkit.getPlayer(args[1])
                if (removed == null) {
                    player.sendMessage(Messages.NOTAPLAYER)
                    return false
                }
                FriendManager.get.removeFriend(player, removed)
            }
            "list" -> {
                val fakePlayer = PlayerManager.get.getFakePlayer(player)!!
                if (fakePlayer.friends.isEmpty()) {
                    player.sendMessage("${ChatColor.RED}You don't have any friends yet!  You can add the by running the command /friend add <player>")
                    return true
                }
                //Eventually I want to have this formatted into pages but for now this will work
                for (friend in fakePlayer.friends) {
                    val status: String = if (friend.isOnline) {
                        "${ChatColor.GREEN}ONLINE"
                    } else {
                        "${ChatColor.RED}OFFLINE"
                    }
                    player.sendMessage("${ChatColor.RED}${friend.name}: $status")
                }
            }
            "help"-> {
                sender.sendMessage("${ChatColor.GOLD}The available commands for Friend are:\n" +
                        "/friend add <player> \n" +
                        "/friend remove <player> \n" +
                        "/friend accept <player> \n" +
                        "/friend deny <player>\n" +
                        "/friend remove <player>\n" +
                        "/friend list")
            }
            else -> {
                player.sendMessage("${ChatColor.RED}That is not an available command! Use /friends help for more information!")
            }
        }
        return true
    }
}