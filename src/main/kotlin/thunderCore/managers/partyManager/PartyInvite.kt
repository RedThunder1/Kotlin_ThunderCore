package thunderCore.managers.partyManager

import net.md_5.bungee.api.chat.ClickEvent
import thunderCore.ThunderCore
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

class PartyInvite {
    private val accept = TextComponent("" + ChatColor.GREEN + "ACCEPT")
    private val deny = TextComponent("" + ChatColor.RED + "DENY")

    init {
        accept.clickEvent = ClickEvent(ClickEvent.Action.RUN_COMMAND, "party join")
        deny.clickEvent = ClickEvent(ClickEvent.Action.RUN_COMMAND, "party deny")
    }

    fun invitePlayer(inviter: Player, invited: Player) {
        invited.sendMessage("" + ChatColor.GOLD + "You have been invited to a party by ${inviter.name}! " + accept + ChatColor.GOLD + " || " + deny)
        var party = PartyManager.get.getPartyByLeader(inviter)
        if (party == null) {
            val invitedList: ArrayList<Player> = ArrayList()
            invitedList.add(invited)
            party = PartyForm(inviter, ArrayList(), invitedList)
            PartyManager.get.createParty(party)
        }
        if (party.members.size == 3) {
            inviter.sendMessage("" + ChatColor.RED + "The party is full! You cannot invite more players!")
            return
        }
        if (party.invited.contains(invited)) {
            inviter.sendMessage("" + ChatColor.RED + "You have already invited ${invited.name}!")
            return
        }
        if (PartyManager.get.checkIfMember(invited)) {
            inviter.sendMessage("" + ChatColor.RED + "${invited.name} is already in a party!")
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
                    invited.sendMessage("" + ChatColor.RED +"Your party invite has expired!")
                    cancel()
                }
                time[0]--
            }
        }.runTaskTimer(ThunderCore.get, 0, 20)
    }
}
