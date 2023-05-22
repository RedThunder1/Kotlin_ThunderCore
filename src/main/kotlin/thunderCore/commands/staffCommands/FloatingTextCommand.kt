package thunderCore.commands.staffCommands

import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import thunderCore.ThunderCore
import thunderCore.managers.floatingtextmanager.FloatingTextManager
import thunderCore.utilities.Messages
import java.lang.StringBuilder

class FloatingTextCommand: CommandExecutor {

    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<out String>?): Boolean {
        if (sender !is Player) {
            sender.sendMessage(Messages.CONSOLECANTUSE)
            return false
        }
        val player: Player = sender.player!!
        if (!ThunderCore.get.isAdmin(player)) {
            player.sendMessage(Messages.NOPERMS)
            return true
        }
        if (args == null || args.isEmpty()) {
            player.sendMessage("${ChatColor.RED}You must provide a sub command! /holo add | /holo remove | /holo edit | /holo move")
            return true
        }
        if (args[0] == "list") {
            if (FloatingTextManager.get.stands.isEmpty()) {
                player.sendMessage("${ChatColor.RED}There are no stands!")
                return true
            }
            player.sendMessage("${ChatColor.GOLD}_______Stands_______")
            for (key in FloatingTextManager.get.stands.keys) {
                player.sendMessage("${ChatColor.GOLD}|| $key")
            }
            return true

        }
        if (args[1].isEmpty()) {
            player.sendMessage("${ChatColor.RED}You must provide an id for the floating text!")
            return true
        }
        val textBuilder = StringBuilder()

        when (args[0]) {
            "add" -> {
                if (args[2].isEmpty()) {
                    player.sendMessage("${ChatColor.RED}You must provide text for the floating text!")
                    return true
                }

                for (str in args.copyOfRange(2, args.size)) { textBuilder.append(str).append(" ") }

                FloatingTextManager.get.createFloatingText(player, args[1], textBuilder.toString())
            }
            "remove" -> {
                player.sendMessage(FloatingTextManager.get.removeFloatingText(args[1]))
            }
            "move" -> {
                val stand = FloatingTextManager.get.getStand(args[1])
                if (stand == null) {
                    player.sendMessage("${ChatColor.RED}That is not an available holo to move!")
                    return true
                }
                stand.stand.teleport(player.location)
            }
            "edit" -> {

                //Create edit function in manager

                val stand = FloatingTextManager.get.getStand(args[1])
                if (stand == null) {
                    player.sendMessage("${ChatColor.RED}That is not an available holo to edit")
                    return true
                }

                if (args[2].isEmpty()) {
                    player.sendMessage("${ChatColor.RED}You must provide new text for the floating text!")
                    return true
                }

                for (str in args.copyOfRange(2, args.size)) { textBuilder.append(str).append(" ") }

                stand.customName = textBuilder.toString()
            }
            else -> {
                player.sendMessage("${ChatColor.RED}That is not a proper sub command! /holo add | /holo remove | /holo edit")
            }
        }
        return true
    }
}