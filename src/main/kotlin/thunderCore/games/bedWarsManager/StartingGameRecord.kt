package thunderCore.games.bedWarsManager

import org.bukkit.World
import org.bukkit.entity.Player
import java.util.*

data class StartingGameRecord(
    val players: ArrayList<Player>,
    val mode: String,
    val map: BedWarsMapForm,
    val id: UUID,
    val lobby: World
)
