package thunderCore.games.bedWarsManager.startingGameManager

import org.bukkit.ChatColor
import org.bukkit.Instrument
import org.bukkit.Note
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.scheduler.BukkitRunnable
import thunderCore.ThunderCore
import thunderCore.games.bedWarsManager.BedWarsManager
import thunderCore.games.bedWarsManager.gamesManager.BedWarsGameForm
import thunderCore.games.bedWarsManager.gamesManager.BedWarsTeamGameManager
import thunderCore.games.bedWarsManager.teamManager.BedWarsTeamForm

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
                ThunderCore.get.redMsg("There was an error initializing the bedwars game!")
                gameForm.players[0].sendMessage("" + ChatColor.RED + "There was an error with initializing the BedWars game! Please try again!")
                BedWarsManager.get.removeStartingGames(this)
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
        for (players in gameForm.players) {
            players.sendMessage("" + ChatColor.GOLD + "${player.name} + has joined the game! [$playerCount/$requiredPlayerCount]")
        }
    }


    private fun gameCountdown() {
        var time = 15
        object : BukkitRunnable() {
            override fun run() {
                for (player in gameForm.players) {
                    player.sendTitle("" + ChatColor.RED + time, null)
                    player.playNote(player.location, Instrument.PIANO, Note.natural(1, Note.Tone.A))
                }
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
        }.runTaskTimer(ThunderCore.get, 0, 20)
    }

    private fun beginGame() {
        //Begin game by loading new game manager and removing this once done
        //Create teams here
        val teams: ArrayList<BedWarsTeamForm> = ArrayList()
        var team1: BedWarsTeamForm
        for (i in 1 until 4) {

        }

        BedWarsTeamGameManager(BedWarsGameForm(teams, gameForm.mode, gameForm.map, gameForm.id))



        BedWarsManager.get.removeStartingGames(this)
    }

}
