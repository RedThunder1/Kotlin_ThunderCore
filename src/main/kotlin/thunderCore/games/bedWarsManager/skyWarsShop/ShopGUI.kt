package thunderCore.games.bedWarsManager.skyWarsShop

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import thunderCore.games.bedWarsManager.BedWarsManager
import thunderCore.games.bedWarsManager.BedWarsTeamForm

class ShopGUI {
    fun banGUI(player: Player) {
        val inventory: Inventory =
            Bukkit.createInventory(null, 54, Component.text("Shop Menu").color(NamedTextColor.GOLD))
        for (i in 0..53) {
            val blackGlass = ItemStack(Material.BLACK_STAINED_GLASS, 1)
            val emptyMeta: ItemMeta = blackGlass.itemMeta
            emptyMeta.displayName(Component.text("-"))
            blackGlass.setItemMeta(emptyMeta)
            inventory.setItem(i, blackGlass)
        }
        var wool: ItemStack? = null
        var glass: ItemStack? = null
        for (gameForm in BedWarsManager.activeGames) {
            val game: BedWarsTeamForm? = gameForm.getTeamByPlayer(player)
            if (game != null) {
                when (game.teamColor) {
                    "blue" -> {
                        wool = ItemStack(Material.BLUE_WOOL, 16)
                        glass = ItemStack(Material.BLUE_STAINED_GLASS, 8)
                    }

                    "red" -> {
                        wool = ItemStack(Material.RED_WOOL, 16)
                        glass = ItemStack(Material.RED_STAINED_GLASS, 8)
                    }

                    "green" -> {
                        wool = ItemStack(Material.GREEN_WOOL, 16)
                        glass = ItemStack(Material.GREEN_STAINED_GLASS, 8)
                    }

                    "yellow" -> {
                        wool = ItemStack(Material.YELLOW_WOOL, 16)
                        glass = ItemStack(Material.YELLOW_STAINED_GLASS, 8)
                    }

                    else -> {
                        return
                    }
                }
            }
        }
        assert(wool != null)
        //Prices will need to be balanced
        val woolMeta: ItemMeta = wool!!.itemMeta
        woolMeta.displayName(Component.text("Wool (4 Iron)"))
        wool.setItemMeta(woolMeta)
        inventory.setItem(1, wool)
        val wood = ItemStack(Material.OAK_WOOD, 8)
        val woodMeta: ItemMeta = wood.itemMeta
        woodMeta.displayName(Component.text("Wood (8 Iron)"))
        wood.setItemMeta(woodMeta)
        inventory.setItem(2, wood)
        val endStone = ItemStack(Material.OAK_WOOD, 8)
        val endStoneMeta: ItemMeta = endStone.itemMeta
        endStoneMeta.displayName(Component.text("EndStone (4 Gold)"))
        endStone.setItemMeta(endStoneMeta)
        inventory.setItem(3, endStone)
        val glassMeta: ItemMeta = glass!!.itemMeta
        glassMeta.displayName(Component.text("Glass (6 Iron)"))
        glass.setItemMeta(glassMeta)
        inventory.setItem(4, glass)
        player.openInventory(inventory)
    }
}