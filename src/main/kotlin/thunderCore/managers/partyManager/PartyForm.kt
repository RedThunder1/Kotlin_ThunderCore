package thunderCore.managers.partyManager

import org.bukkit.entity.Player

data class PartyForm(var leader: Player, var members: ArrayList<Player>, var invited: ArrayList<Player>)
