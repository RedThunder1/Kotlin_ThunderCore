package thunderCore.games.bedWarsManager.bedWarsGenerator

import thunderCore.ThunderCore
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable

class SpawnGenerator(private val location: Location) {
    private var spawnItems = true
    private var defaultIronSpawnRate = 2f
    private var defaultGoldSpawnRate = 10f
    private val world: World = location.world!!

    fun startSpawning() {
        val ironSpawnRate = floatArrayOf(2f)
        val goldSpawnRate = floatArrayOf(10f)
        val iron = ItemStack(Material.IRON_INGOT)
        val gold = ItemStack(Material.GOLD_INGOT)
        object : BukkitRunnable() {
            override fun run() {
                if (!spawnItems) {
                    cancel()
                }
                if (ironSpawnRate[0] <= 0) {
                    world.dropItemNaturally(location, iron)
                    ironSpawnRate[0] = defaultIronSpawnRate
                } else {
                    ironSpawnRate[0]--
                }
                if (goldSpawnRate[0] <= 0) {
                    world.dropItemNaturally(location, gold)
                    goldSpawnRate[0] = defaultGoldSpawnRate
                } else {
                    goldSpawnRate[0]--
                }
            }
        }.runTaskTimer(ThunderCore.get, 0, 20)
    }

    fun endSpawn() {
        spawnItems = false
    }

    fun upgradeIronSpawnRate(upgradeRate: Float) {
        defaultIronSpawnRate -= upgradeRate
    }

    fun upgradeGoldSpawnRate(upgradeRate: Float) {
        defaultGoldSpawnRate -= upgradeRate
    }
}
