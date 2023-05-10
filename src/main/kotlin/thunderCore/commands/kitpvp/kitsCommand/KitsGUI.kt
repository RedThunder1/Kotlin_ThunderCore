package thunderCore.commands.kitpvp.kitsCommand

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import thunderCore.ThunderCore
import thunderCore.games.kitpvpManager.KitPvPManager

object KitsGUI {

    fun kitsGui(player: Player) {
        val inventory: Inventory = ThunderCore.get.server.createInventory(null, 54, Component.text().build()
            .color(NamedTextColor.BLUE)
            .decorate(TextDecoration.BOLD)
            .content("Kits Menu"))
        for (kit in KitPvPManager.kits) {
            inventory.addItem(kit.logo)
        }
        player.openInventory(inventory)
    }

}