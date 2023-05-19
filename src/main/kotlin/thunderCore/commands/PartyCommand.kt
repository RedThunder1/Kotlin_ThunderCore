package thunderCore.commands

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import thunderCore.managers.partyManager.PartyForm
import thunderCore.managers.partyManager.PartyInvite
import thunderCore.managers.partyManager.PartyManager
import thunderCore.utilities.Messages
import org.bukkit.entity.Player as Player

class PartyCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage(Messages.CONSOLECANTUSE)
            return true
        }
        val player = sender.player!!
        val party: PartyForm? = PartyManager.get.getPartyByLeader(player)
        when (args[0]) {
            "invite" -> {
                if (args[1].isEmpty()) {
                    player.sendMessage("" + ChatColor.RED + "You must provide a player to invite!")
                    return false
                }
                if (Bukkit.getPlayer(args[1]) == null) {
                    player.sendMessage("" + ChatColor.RED + "That is not a player!")
                    return true
                }
                val invited: Player = Bukkit.getPlayer(args[1])!!
                PartyInvite().invitePlayer(player, invited)
            }

            "kick" -> {
                if (party == null) {
                    player.sendMessage("" + ChatColor.RED + "You cannot kick a player as you are either not in a party or not the leader!")
                    return true
                }
                if (args[1].isEmpty()) {
                    player.sendMessage("" + ChatColor.RED + "You must provide a player to Kick!")
                    return false
                }
                if (Bukkit.getPlayer(args[1]) == null) {
                    player.sendMessage("" + ChatColor.RED + "That is not a player!")
                    return true
                }
                val kicked: Player = Bukkit.getPlayer(args[1])!!
                if (party.members.isEmpty()) {
                    player.sendMessage("" + ChatColor.RED + "There are no players to kick!")
                }
                if (!(party.members.contains(kicked))) {
                    player.sendMessage("" + ChatColor.RED + "That player is not in your party!")
                    return true
                }
                party.members.remove(kicked)
                kicked.sendMessage("" + ChatColor.RED + "You were kicked from the server!")
            }

            "disband" -> {
                if (party == null) {
                    player.sendMessage("" + ChatColor.RED + "You cannot disband a party as you are either not in one or not the leader!")
                    return true
                }
                player.sendMessage("" + ChatColor.RED + "The party has been disbanded")
                if (party.members.isEmpty()) {
                    for (p in party.members) {
                        p.sendMessage("" + ChatColor.RED + "The party has been disbanded")
                    }
                }
                PartyManager.get.removeParty(party)
            }

            "leave" -> {
                if (party == null) {
                    val party2 = PartyManager.get.getPartyByMember(player)
                    if (party2 == null) {
                        player.sendMessage("" + ChatColor.RED + "You are not in a party!")
                        return true
                    }
                    player.sendMessage("" + ChatColor.RED + "You have left the party!")
                    party2.members.remove(player)
                    if (party2.members.isNotEmpty()) {
                        for (players in party2.members) {
                            players.sendMessage("${ChatColor.RED}${player.name} has left the party!")
                        }
                    }
                    return true
                }

                if (party.members.isEmpty()) {
                    player.sendMessage("${ChatColor.RED}You have disbanded the party!")
                    PartyManager.get.removeParty(party)
                    return true
                }
                val newLeader = party.members.firstOrNull()!!
                party.members.remove(newLeader)
                party.leader = newLeader
                newLeader.sendMessage("${ChatColor.RED}${player.name} has left the party!  You are the new party leader")
                if (party.members.isNotEmpty()) {
                    for (players in party.members) {
                        players.sendMessage("${ChatColor.RED}${player.name} has left the party!")
                        players.sendMessage("${ChatColor.GREEN}${newLeader.name} is the new leader!")
                    }
                }
                return true
            }

            "join" -> {
                val invitedPartyForm: PartyForm? = PartyManager.get.checkInvited(player)
                if (invitedPartyForm == null) {
                    player.sendMessage("${ChatColor.RED}You have not been invited to a party!")
                    return true
                }
                invitedPartyForm.members.add(sender)
                invitedPartyForm.invited.remove(sender)
                for (p in invitedPartyForm.members) {
                    p.sendMessage("${ChatColor.GREEN}${sender.name} has joined the party!")
                }
                return true
            }

            "deny" -> {
                val invitedPartyForm: PartyForm? = PartyManager.get.checkInvited(sender)
                if (invitedPartyForm == null) {
                    sender.sendMessage("${ChatColor.GREEN}You have not been invited to a party!")
                    return true
                }

                //Remove player from party form
                return true
            }

            "help" -> {
                sender.sendMessage("${ChatColor.GOLD}The available commands for Party are:\n" +
                        "/party invite <player> \n" +
                        "/party kick <player> \n" +
                        "/party disband \n" +
                        "/party leave \n" +
                        "/party join \n" +
                        "/party deny")
                return true
            }
            else -> {
                sender.sendMessage("${ChatColor.RED}That is not an available party command!")
                return false
            }
        }
        return true
    }
}
