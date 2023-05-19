package thunderCore.commands.staffCommands.worlds

import org.bukkit.Material
import org.bukkit.generator.ChunkGenerator
import org.bukkit.generator.WorldInfo
import java.util.*


class VoidWorldGenerator : ChunkGenerator() {
    override fun generateSurface(info: WorldInfo, random: Random, x: Int, z: Int, data: ChunkData) {
        for (y in info.minHeight until info.maxHeight) {
            data.setBlock(x, y, z, Material.AIR)
        }
    }

    override fun shouldGenerateNoise(): Boolean {
        return false
    }

    override fun shouldGenerateBedrock(): Boolean {
        return false
    }

    override fun shouldGenerateCaves(): Boolean {
        return false
    }
}
