package thunderCore.games.bedWarsManager

import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.entity.Player
import thunderCore.games.bedWarsManager.gamesManager.BedWarsGameForm
import thunderCore.games.bedWarsManager.mapManager.BedWarsMapForm
import thunderCore.games.bedWarsManager.startingGameManager.StartingGameManager
import thunderCore.managers.ThunderManager
import java.util.*
import kotlin.collections.ArrayList

object BedWarsManager : ThunderManager {
    private val lobbyTemplate: World = Bukkit.getWorld("lobbyTemplate")!!
    private val teamMaps = ArrayList<BedWarsMapForm>()
    private val duelMaps = ArrayList<BedWarsMapForm>()
    val activeGames = ArrayList<BedWarsGameForm>()
    private val startingGames = ArrayList<StartingGameManager>()

    init {
        initializeMaps()
    }

    fun removeActiveGames(removeGame: BedWarsGameForm) {
        activeGames.remove(removeGame)
    }

    fun removeStartingGames(startingGame: StartingGameManager) {
        startingGames.remove(startingGame)
    }

    fun joinGame(player: Player, mode: String) {
        if (startingGames.isEmpty()) {
            initializeNewGame(player, mode)
            return
        }
        var gameToJoin: StartingGameManager? = null
        for (startingGame in startingGames) {
            if (startingGame.gameForm.mode == mode) {
                if (gameToJoin == null) {
                    gameToJoin = startingGame
                } else if (startingGame.playerCount > gameToJoin.playerCount){
                    gameToJoin = startingGame
                }
            }
        }
        if (gameToJoin == null) {
            initializeNewGame(player, mode)
            return
        }
        gameToJoin.addPlayerToLobby(player)
    }

    private fun initializeNewGame(player: Player, mode: String) {
        val players: ArrayList<Player> = ArrayList()
        players.add(player)
        val map = teamMaps[0]
        val lobbyID = UUID.randomUUID()
        val newLobby: BedWarsMapForm

        //startingGames.add(StartingGameManager(StartingGameRecord(players, mode, map, lobbyID, newLobby)));
        return;
    }

    private fun initializeMaps() {
        //Maps will be hardcoded
    }
}
