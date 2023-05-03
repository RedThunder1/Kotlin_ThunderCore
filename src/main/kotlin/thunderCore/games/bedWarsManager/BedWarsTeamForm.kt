package thunderCore.games.bedWarsManager

import org.bukkit.entity.Player

data class BedWarsTeamForm(var teamMembers: ArrayList<Player>, var teamColor: String) {
    private val playerKills: HashMap<Player, Int?> = HashMap()

    init {
        for (player in teamMembers) {
            playerKills[player] = 0
        }
    }

    fun getPlayerKills(player: Player): Int? {
        return playerKills[player]
    }

    fun addPlayerKill(player: Player) {
        if (playerKills[player] == null) {
            return
        }
        playerKills[player] = playerKills[player]!! + 1
    }
}
