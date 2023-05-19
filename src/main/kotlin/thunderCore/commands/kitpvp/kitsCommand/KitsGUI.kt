package thunderCore.commands.kitpvp.kitsCommand

import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import thunderCore.ThunderCore
import thunderCore.games.kitpvpManager.KitPvPManager

class KitsGUI {

    fun kitsGui(player: Player) {
        val inventory: Inventory = ThunderCore.get.server.createInventory(null, 54, "${ChatColor.BOLD}${ChatColor.BLUE}Kits Menu")
        for (kit in KitPvPManager.get.kits) {
            inventory.addItem(kit.logo)
        }
        player.openInventory(inventory)
    }

}