package thunderCore.managers.partyManager

import org.bukkit.entity.Player

object PartyManager {

    private var parties: ArrayList<PartyForm> = ArrayList()

    fun createParty(partyForm: PartyForm) {
        parties.add(partyForm)
    }

    fun removeParty(partyForm: PartyForm) {
        parties.remove(partyForm)
    }

    fun getPartyByLeader(player: Player): PartyForm? {
        for (partyForm in parties) {
            if (partyForm.leader.toString() == player.toString()) {
                return partyForm
            }
        }
        return null
    }

    fun getPartyByMember(player: Player?): PartyForm? {
        for (partyForm in parties) {
            if (partyForm.members.contains(player!!)) {
                return partyForm
            }
        }
        return null
    }

    fun checkInvited(player: Player?): PartyForm? {
        for (partyForm in parties) {
            if (partyForm.invited.contains(player)) {
                return partyForm
            }
        }
        return null
    }

    fun checkIfMember(player: Player): Boolean {
        for ((leader, members, invited) in parties) {
            if (members.contains(player)) {
                return true
            }
            if (invited.contains(player)) {
                return true
            }
            if (leader == player) {
                return true
            }
        }
        return false
    }
}
