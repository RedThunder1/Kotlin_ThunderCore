package thunderCore.commands.staffCommands.worlds

import org.bukkit.block.Biome
import org.bukkit.generator.BiomeProvider
import org.bukkit.generator.WorldInfo


class PlainsProvider: BiomeProvider() {

    override fun getBiome(p0: WorldInfo, x: Int, y: Int, z: Int): Biome {
        return Biome.PLAINS
    }

    override fun getBiomes(p0: WorldInfo): List<Biome> {
        return listOf(Biome.PLAINS)
    }

}