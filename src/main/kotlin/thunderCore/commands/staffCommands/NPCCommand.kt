package thunderCore.commands.staffCommands

import net.minecraft.core.Position
import net.minecraft.server.level.EntityPlayer
import net.minecraft.server.level.WorldServer
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import thunderCore.ThunderCore
import thunderCore.managers.npcmanager.NPCManager
import thunderCore.utilities.Messages

class NPCCommand: CommandExecutor {
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
            player.sendMessage("${ChatColor.RED}You must provide a subcommand! (add|move|edit|remove)")
            return false
        }
        if (args[1].isEmpty()) {
            player.sendMessage("${ChatColor.RED}You must provide an id!")
            return false
        }
        val textBuilder = StringBuilder()
        when (args[0]) {
            "add"-> {
                if (args[2].isEmpty()) {
                    player.sendMessage("${ChatColor.RED}You must provide a name!")
                    return false
                }
                for (str in args.copyOfRange(2, args.size)) { textBuilder.append(str).append(" ") }
                NPCManager.get.createNPC(args[1], textBuilder.toString(), player)
            }
            "remove"-> {
                val npc: EntityPlayer? = NPCManager.get.getNPCByID(args[1])
                if (npc == null) {
                    player.sendMessage("${ChatColor.RED}That is not an available npc!")
                    return false
                }
                for (players in Bukkit.getOnlinePlayers()) {
                    NPCManager.get.removeNPCPacket(npc, players)
                }
            }
            "edit"-> {
                if (args[2].isEmpty()) {
                    player.sendMessage("${ChatColor.RED}You must provide a name!")
                    return false
                }
                for (str in args.copyOfRange(2, args.size)) { textBuilder.append(str).append(" ") }
                val npc: EntityPlayer? = NPCManager.get.getNPCByID(args[1])
                if (npc == null) {
                    player.sendMessage("${ChatColor.RED}That is not an available npc!")
                    return false
                }
                npc.displayName = textBuilder.toString()
            }
            "move"-> {
                val npc: EntityPlayer? = NPCManager.get.getNPCByID(args[1])
                if (npc == null) {
                    player.sendMessage("${ChatColor.RED}That is not an available npc!")
                    return false
                }
                npc.teleportTo(player.world as WorldServer, Position(player.location.x, player.location.y, player.location.z))
            }
            "list"-> {
                if (NPCManager.get.npcs.isEmpty()) {
                    player.sendMessage("${ChatColor.RED}There are no npcs!")
                }
                player.sendMessage("${ChatColor.GOLD}_______Npcs_______")
                for (npc in NPCManager.get.npcs.keys) { player.sendMessage("${ChatColor.GOLD}|| $npc") }
            }
            else -> {
                player.sendMessage("${ChatColor.RED}That is not an available sub command!")
            }
        }


        return true
    }
}