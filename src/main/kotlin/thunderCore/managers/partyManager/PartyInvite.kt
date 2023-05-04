package thunderCore.managers.partyManager

import thunderCore.ThunderCore
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

class PartyInvite {
    private val accept = Component.text("ACCEPT", NamedTextColor.GREEN)
        .clickEvent(ClickEvent.runCommand("party join"))
        .toBuilder().build()
    private val deny = Component.text("DENY", NamedTextColor.RED)
        .clickEvent(ClickEvent.runCommand("party deny"))
        .toBuilder().build()

    fun invitePlayer(inviter: Player, invited: Player) {
        invited.sendMessage(Component.text().build()
            .color(NamedTextColor.GOLD)
            .content("You have been invited to a party by ${inviter.name}! ")
            .append(accept)
            .append(Component.text(" || ", NamedTextColor.GOLD))
            .append(deny))
        var party = PartyManager.getPartyByLeader(inviter)
        if (party == null) {
            val invitedList: ArrayList<Player> = ArrayList()
            invitedList.add(invited)
            party = PartyForm(inviter, ArrayList(), invitedList)
            PartyManager.createParty(party)
        }
        if (party.members.size == 3) {
            inviter.sendMessage(Component.text("The party is full! You cannot invite more players!").color(NamedTextColor.RED))
            return
        }
        if (party.invited.contains(invited)) {
            inviter.sendMessage(Component.text("You have already invited " + invited.name).color(NamedTextColor.RED))
            return
        }
        if (PartyManager.checkIfMember(invited)) {
            inviter.sendMessage(Component.text(invited.name + " is already in a party!").color(NamedTextColor.RED))
            return
        }
        val time = intArrayOf(60)
        object : BukkitRunnable() {
            override fun run() {
                if (!party.invited.contains(invited)) {
                    cancel()
                }
                if (time[0] == 0) {
                    party.invited.remove(invited)
                    invited.sendMessage(Component.text("Your party invite has expired!").color(NamedTextColor.RED))
                    cancel()
                }
                time[0]--
            }
        }.runTaskTimer(ThunderCore.get(), 0, 20)
    }
}
