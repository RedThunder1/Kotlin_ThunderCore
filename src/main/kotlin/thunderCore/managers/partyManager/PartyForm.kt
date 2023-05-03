package thunderCore.managers.partyManager

import org.bukkit.entity.Player

data class PartyForm(var leader: Player, var members: ArrayList<Player>, var invited: ArrayList<Player>) {

    fun removeMember(player: Player) {
        if (members.isEmpty()) { return }
        for (p in members) {
            if (p == player) {
                members.remove(player)
                return
            }
        }
    }

    fun removeInvited(player: Player) {
        for (p in invited) {
            if (p == player) {
                invited.remove(player)
                return
            }
        }
    }
}
