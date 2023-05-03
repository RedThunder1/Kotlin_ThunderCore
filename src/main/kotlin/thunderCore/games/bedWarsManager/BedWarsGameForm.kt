package thunderCore.games.bedWarsManager

import org.bukkit.entity.Player

data class BedWarsGameForm(
    var teams: ArrayList<BedWarsTeamForm>,
    val mode: String,
    val map: BedWarsMapForm,
    var id: String
) {

    fun getTeamByColor(color: String): BedWarsTeamForm? {
        for (teamForm in teams) {
            if (teamForm.teamColor == color) {
                return teamForm
            }
        }
        return null
    }

    fun getTeamByPlayer(player: Player?): BedWarsTeamForm? {
        for (teamForm in teams) {
            if (teamForm.teamMembers.contains(player)) {
                return teamForm
            }
        }
        return null
    }

    fun removeTeam(removedTeam: BedWarsTeamForm) {
        teams.remove(removedTeam)
    }
}
