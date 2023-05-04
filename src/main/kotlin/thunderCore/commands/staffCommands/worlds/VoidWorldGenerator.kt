package thunderCore.commands.staffCommands.worlds

import org.bukkit.World
import org.bukkit.generator.ChunkGenerator
import java.util.*

class VoidWorldGenerator : ChunkGenerator() {
    @SuppressWarnings
    override fun generateChunkData(world: World, random: Random, x: Int, z: Int, biome: BiomeGrid): ChunkData {
        return createChunkData(world)
    }
}
