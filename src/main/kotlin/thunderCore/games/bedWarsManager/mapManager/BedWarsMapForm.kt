package thunderCore.games.bedWarsManager.mapManager

import org.bukkit.Location
import org.bukkit.World
import java.util.*

data class BedWarsMapForm(
    val name: String,
    val mapTemplate: World,
    val worldType: String,
    val teamSpawns: List<Location>,
    val teamShops: List<Location>,
    val centerGenerators: List<Location>,
    val upgradeGenerators: List<Location>
)
