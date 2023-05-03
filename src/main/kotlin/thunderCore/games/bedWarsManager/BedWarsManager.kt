package thunderCore.games.bedWarsManager

import com.google.gson.Gson
import org.bukkit.World
import org.bukkit.entity.Player
import thunderCore.ThunderCore
import thunderCore.games.bedWarsManager.gameManager.BedWarsDuelGameManager
import thunderCore.games.bedWarsManager.gameManager.BedWarsTeamGameManager
import thunderCore.managers.ThunderManager

object BedWarsManager : ThunderManager {
    private val gson: Gson = Gson()
    private val lobbyTemplate: World? = null
    private val teamMaps = ArrayList<BedWarsMapForm>()
    private val duelMaps = ArrayList<BedWarsMapForm>()
    val activeGames = ArrayList<BedWarsGameForm>()
    private val startingGames = ArrayList<StartingGameRecord>()

    init {
        initializeMaps()
    }

    fun removeActiveGames(removeGame: BedWarsGameForm) {
        activeGames.remove(removeGame)
    }

    fun removeStartingGames(startingGame: StartingGameRecord) {
        startingGames.remove(startingGame)
    }

    fun startGame(gameForm: BedWarsGameForm) {
        activeGames.add(gameForm)
        when (gameForm.mode) {
            "quads", "trios", "duos", "solo" -> BedWarsTeamGameManager(gameForm)
            "duel" -> BedWarsDuelGameManager(gameForm)
            else -> ThunderCore.get().redMsg("There was an error initializing aSkyWars game!")
        }
    }

    fun joinGame(player: Player, mode: String) {
        if (startingGames.isEmpty()) {
            initializeNewGame(player, mode)
        }
        for (startingGame in startingGames) {
            if (startingGame.mode == mode) {
                startingGame.players.add(player)
                //tp player to lobby
                return
            }
        }
        initializeNewGame(player, mode)
    }

    private fun initializeNewGame(player: Player, mode: String) {
        val players: ArrayList<Player?> = ArrayList<Player?>()
        players.add(player)
        /*
        Get random map here once maps are made


        startingGames.add(new StartingGameRecord(players, mode, map, UUID.randomUUID()));
        return;
        */
    }

    private fun initializeMaps() {
        //Maps will be hardcoded
        //lobbyTemplate = Bukkit.getWorld("lobbyTemplate");
    }
}
