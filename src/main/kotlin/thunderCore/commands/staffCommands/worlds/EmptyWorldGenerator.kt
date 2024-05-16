package thunderCore.commands.staffCommands.worlds

import org.bukkit.World
import org.bukkit.generator.ChunkGenerator
import java.util.*
import javax.annotation.Nonnull


class EmptyChunkGenerator : ChunkGenerator() {
    @Nonnull
    override fun generateChunkData(@Nonnull world: World, @Nonnull random: Random, x: Int, z: Int, @Nonnull biome: BiomeGrid): ChunkData {
        return createChunkData(world)
    }
}