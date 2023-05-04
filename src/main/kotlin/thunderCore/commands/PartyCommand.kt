package thunderCore.commands

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
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
        val party: PartyForm? = PartyManager.getPartyByLeader(player)
        when (args[0]) {
            "invite" -> {
                if (args[1].isEmpty()) {
                    player.sendMessage(Component.text("You must provide a player to invite!", NamedTextColor.RED))
                    return false
                }
                if (Bukkit.getPlayer(args[1]) == null) {
                    player.sendMessage(Component.text("That is not a player!", NamedTextColor.RED))
                    return true
                }
                val invited: Player = Bukkit.getPlayer(args[1])!!
                PartyInvite().invitePlayer(player, invited)
            }

            "kick" -> {
                if (party == null) {
                    player.sendMessage(Component.text("You cannot kick a player as you are either not in a party or not the leader!", NamedTextColor.RED))
                    return true
                }
                if (args[1].isEmpty()) {
                    player.sendMessage(Component.text("You must provide a player to Kick!", NamedTextColor.RED))
                    return false
                }
                if (Bukkit.getPlayer(args[1]) == null) {
                    player.sendMessage(Component.text("That is not a player!", NamedTextColor.RED))
                    return true
                }
                val kicked: Player = Bukkit.getPlayer(args[1])!!
                if (party.members.isEmpty()) {
                    player.sendMessage(Component.text("There are no players to kick!", NamedTextColor.RED))
                }
                if (!(party.members.contains(kicked))) {
                    player.sendMessage(Component.text("That player is not in your party!", NamedTextColor.RED))
                    return true
                }
                party.members.remove(kicked)
                kicked.sendMessage(Component.text("You were kicked from the server!", NamedTextColor.RED))
            }

            "disband" -> {
                if (party == null) {
                    player.sendMessage(Component.text("You cannot disband a party as you are either not in one or not the leader!", NamedTextColor.RED))
                    return true
                }
                player.sendMessage(Component.text("The party has been disbanded", NamedTextColor.RED))
                if (party.members.isEmpty()) {
                    for (p in party.members) {
                        p.sendMessage(Component.text("The party has been disbanded", NamedTextColor.RED))
                    }
                }
                PartyManager.removeParty(party)
            }

            "leave" -> {
                if (party == null) {
                    val party2 = PartyManager.getPartyByMember(player)
                    if (party2 == null) {
                        player.sendMessage(Component.text("You are not in a party!", NamedTextColor.RED))
                        return true
                    }
                    player.sendMessage(Component.text("You have left the party!", NamedTextColor.RED))
                    party2.members.remove(player)
                    if (party2.members.isNotEmpty()) {
                        for (players in party2.members) {
                            players.sendMessage(Component.text("${player.name} has left the party!", NamedTextColor.RED))
                        }
                    }
                    return true
                }

                if (party.members.isEmpty()) {
                    player.sendMessage(Component.text("You have disbanded the party!", NamedTextColor.RED))
                    PartyManager.removeParty(party)
                    return true
                }
                val newLeader = party.members.firstOrNull()!!
                party.members.remove(newLeader)
                party.leader = newLeader
                newLeader.sendMessage(Component.text("${player.name} has left the party!  You are the new party leader", NamedTextColor.GREEN))
                if (party.members.isNotEmpty()) {
                    for (players in party.members) {
                        players.sendMessage(Component.text("${player.name} has left the party!", NamedTextColor.RED))
                        players.sendMessage(Component.text("${newLeader.name} is the new leader!", NamedTextColor.GREEN))
                    }
                }
                return true
            }

            "join" -> {
                val invitedPartyForm: PartyForm? = PartyManager.checkInvited(player)
                if (invitedPartyForm == null) {
                    player.sendMessage(Component.text("You have not been invited to a party!", NamedTextColor.RED))
                    return true
                }
                invitedPartyForm.members.add(sender)
                invitedPartyForm.invited.remove(sender)
                for (p in invitedPartyForm.members) {
                    p.sendMessage(Component.text("${sender.name} has joined the party!", NamedTextColor.GREEN))
                }
                return true
            }

            "deny" -> {
                val invitedPartyForm: PartyForm? = PartyManager.checkInvited(sender)
                if (invitedPartyForm == null) {
                    sender.sendMessage(Component.text("You have not been invited to a party!", NamedTextColor.GREEN))
                    return true
                }

                //Remove player from party form
                return true
            }

            "help" -> {
                sender.sendMessage(Component.text("The available commands for Party are:\n" +
                        "/party invite <player> \n" +
                        "/party kick <player> \n" +
                        "/party disband \n" +
                        "/party leave \n" +
                        "/party join \n" +
                        "/party deny",
                    NamedTextColor.GOLD))
                return true
            }

            else -> {
                sender.sendMessage(Component.text("That is not an available party command!", NamedTextColor.RED))
                return false
            }
        }
        return true
    }
}
