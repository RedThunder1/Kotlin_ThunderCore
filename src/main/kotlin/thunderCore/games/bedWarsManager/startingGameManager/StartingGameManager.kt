package thunderCore.games.bedWarsManager.startingGameManager

import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.scheduler.BukkitRunnable
import thunderCore.ThunderCore
import thunderCore.games.bedWarsManager.BedWarsManager

class StartingGameManager(startingGameForm: StartingGameRecord): Listener{
    private var starting = false
    val gameForm = startingGameForm
    var playerCount = gameForm.players.size
    private var requiredPlayerCount: Int? = null

    init {
        when(gameForm.mode) {
            "quad" -> { requiredPlayerCount = 14 }
            "trios" -> { requiredPlayerCount = 10 }
            "duos" -> { requiredPlayerCount = 8 }
            "solo" -> { requiredPlayerCount = 4 }
            else -> {
                ThunderCore.redMsg("There was an error initializing the bedwars game!")
                gameForm.players[0].sendMessage(Component.text("There was an error with initializing the BedWars game! Please try again!"))
                BedWarsManager.removeStartingGames(this)
            }
        }

        for (player in gameForm.players) {
            //Teleport player to bed wars lobby world
            player.teleport(gameForm.lobby.spawnLocation)
        }
    }


    fun addPlayerToLobby(player: Player) {
        player.teleport(gameForm.lobby.spawnLocation)
        gameForm.players.add(player)
        playerCount++
        if (playerCount > requiredPlayerCount!! && !starting) {
            //Start countdown if lobby has at least 14 players
            gameCountdown()
        }
    }


    private fun gameCountdown() {
        var time = 15
        object : BukkitRunnable() {
            override fun run() {
                if (time == 0) {
                    beginGame()
                    cancel()
                }else if (playerCount < requiredPlayerCount!!) {
                    cancel()
                } else {
                    time--
                }
                //Show countdown for players and if a playerCount decreases below 14 stop countdown
            }
        }.runTaskTimer(ThunderCore, 0, 20)
    }

    private fun beginGame() {
        //Begin game by loading new game manager and removing this once done




        BedWarsManager.removeStartingGames(this)
    }

}
