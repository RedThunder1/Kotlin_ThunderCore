package thunderCore.games.bedWarsManager.bedWarsGenerator

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable
import thunderCore.ThunderCore

class CenterGenerator(private val location: Location) {
    private var spawnItems = true
    private var defaultSpawnRate = 30f
    private val world: World = location.world

    fun startSpawning() {
        val spawnRate = floatArrayOf(30f)
        val emerald = ItemStack(Material.EMERALD)
        object : BukkitRunnable() {
            override fun run() {
                if (!spawnItems) {
                    cancel()
                }
                if (spawnRate[0] <= 0) {
                    world.dropItemNaturally(location, emerald)
                    spawnRate[0] = defaultSpawnRate
                } else {
                    spawnRate[0]--
                }
            }
        }.runTaskTimer(ThunderCore.get, 0, 20)
    }

    fun endSpawn() {
        spawnItems = false
    }

    fun upgradeSpawnRate(upgradeRate: Float) {
        defaultSpawnRate -= upgradeRate
    }
}
