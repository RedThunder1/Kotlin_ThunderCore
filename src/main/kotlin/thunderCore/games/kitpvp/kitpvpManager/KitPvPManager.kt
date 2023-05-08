package thunderCore.games.kitpvp.kitpvpManager

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.ArrayList


object KitPvPManager {

    private var players = ArrayList<Player>()
    private val spawn = Location(Bukkit.getWorld("kitpvp"), 0.5, 72.0, 0.5)

    fun playerJoin(player: Player) {
        players.add(player)
        player.teleport(spawn)
    }
    fun removePlayer(player: Player) { players.remove(player) }

    fun ironKit(): List<ItemStack>? {
        val ironKit = ArrayList<ItemStack>()
        ironKit.add(ItemStack(Material.IRON_HELMET))
        ironKit.add(ItemStack(Material.IRON_CHESTPLATE))
        ironKit.add(ItemStack(Material.IRON_LEGGINGS))
        ironKit.add(ItemStack(Material.IRON_BOOTS))
        ironKit.add(ItemStack(Material.IRON_SWORD))
        ironKit.add(ItemStack(Material.COOKED_BEEF, 16))
        return ironKit
    }

    fun medKit(): List<ItemStack>? {
        val medKit = ArrayList<ItemStack>()
        medKit.add(ItemStack(Material.DIAMOND_HELMET))
        medKit.add(ItemStack(Material.IRON_CHESTPLATE))
        medKit.add(ItemStack(Material.IRON_LEGGINGS))
        medKit.add(ItemStack(Material.IRON_BOOTS))
        medKit.add(ItemStack(Material.DIAMOND_SWORD))
        medKit.add(ItemStack(Material.COOKED_BEEF, 16))
        return medKit
    }

    fun diamondKit(): List<ItemStack>? {
        val medKit = ArrayList<ItemStack>()
        medKit.add(ItemStack(Material.DIAMOND_HELMET))
        medKit.add(ItemStack(Material.DIAMOND_CHESTPLATE))
        medKit.add(ItemStack(Material.DIAMOND_LEGGINGS))
        medKit.add(ItemStack(Material.DIAMOND_BOOTS))
        medKit.add(ItemStack(Material.DIAMOND_SWORD))
        medKit.add(ItemStack(Material.COOKED_BEEF, 16))
        return medKit
    }

}