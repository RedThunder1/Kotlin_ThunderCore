package thunderCore.commands

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import thunderCore.managers.partyManager.PartyForm
import thunderCore.managers.partyManager.PartyInvite
import thunderCore.managers.partyManager.PartyManager
import thunderCore.utilities.Messages

class PartyCommand: CommandExecutor{

    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage(Messages.CONSOLECANTUSE)
            return true
        }
        val party: PartyForm? = PartyManager.get().getPartyByLeader(sender)
        when (args[0]) {
            "invite" -> {
                if (args[1].isEmpty()) {
                    sender.sendMessage(Component.text("You must provide a player to invite!").color(NamedTextColor.RED))
                    return false
                }
                if (Bukkit.getPlayer(args[1]) == null) {
                    sender.sendMessage(Component.text("That is not a player!").color(NamedTextColor.RED))
                    return true
                }
                val invited = Bukkit.getPlayer(args[1])!!
                PartyInvite().invitePlayer(sender, invited)
            }

            "kick" -> {
                if (party == null) {
                    sender.sendMessage(Component.text("You cannot kick a player as you are either not in a party or not the leader!").color(NamedTextColor.RED))
                    return true
                }
                if (party.members == null) {
                    sender.sendMessage(Component.text().build()
                        .color(NamedTextColor.RED)
                        .content("There are no members for you to kick!"))
                }
                if (args[1].isEmpty()) {
                    sender.sendMessage(Component.text("You must provide a player to Kick!").color(NamedTextColor.RED))
                    return false
                }
                if (Bukkit.getPlayer(args[1]) == null) {
                    sender.sendMessage(Component.text("That is not a player!").color(NamedTextColor.RED))
                    return true
                }
                val kicked = Bukkit.getPlayer(args[1])!!
                if (!party.members?.contains(kicked)!!) {
                    sender.sendMessage(Component.text("That player is not in your party!").color(NamedTextColor.RED))
                    return true
                }
                party.removeMember(kicked)
                kicked.sendMessage(Component.text("You were kicked from the server!").color(NamedTextColor.RED))
            }

            "disband" -> {
                if (party == null) {
                    sender.sendMessage(Component.text("You cannot disband a party as you are either not in one or not the leader!").color(NamedTextColor.RED))
                    return true
                }
                if (party.members != null) {
                    for (p in party.members!!) { p.sendMessage(Component.text("The party has been disbanded").color(NamedTextColor.RED)) }
                    PartyManager.get().removeParty(party)
                }
            }

            "leave" -> {
                return true
            }

            "join" -> {
                val invitedPartyForm: PartyForm? = PartyManager.checkInvited(sender)
                if (invitedPartyForm == null) {
                    sender.sendMessage(Component.text("You have not been invited to a party!").color(NamedTextColor.RED))
                    return true
                }
                invitedPartyForm.members?.add(sender)
                invitedPartyForm.invited.remove(sender)
                for (p in invitedPartyForm.members!!) {
                    p.sendMessage(Component.text(" has joined the party!").color(NamedTextColor.GREEN))
                }
                return true
            }

            "deny" -> {
                val invitedPartyForm: PartyForm? = PartyManager.checkInvited(sender)
                if (invitedPartyForm == null) {
                    sender.sendMessage(Component.text("You have not been invited to a party!").color(NamedTextColor.RED))
                    return true
                }

                //Remove player from party form
                return true
            }

            "help" -> {
                sender.sendMessage(Component.text("The available commands for Party are:\n/party invite <player> \n/party kick <player> \n/party disband \n/party leave \n/party join \n/party deny").color(NamedTextColor.GOLD))
                return true
            }

            else -> {
                sender.sendMessage(Component.text("That is not an available party command!").color(NamedTextColor.RED))
                return false
            }
        }
        return true
    }

}