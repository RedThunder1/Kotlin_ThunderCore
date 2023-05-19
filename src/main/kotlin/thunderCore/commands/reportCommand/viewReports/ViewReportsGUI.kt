package thunderCore.commands.reportCommand.viewReports

import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import thunderCore.ThunderCore
import thunderCore.managers.reportManager.ReportManager

class ViewReportsGUI {
    companion object {
        lateinit var get: ViewReportsGUI
    }
    init { get = this }
    fun reportsGui(player: Player) {
        val inventory: Inventory = ThunderCore.get.server.createInventory(null, 54, "${ChatColor.BOLD}${ChatColor.RED}Reports Menu")
        for (report in ReportManager.get.reports) {
            val itemStack = ItemStack(Material.REDSTONE_BLOCK)
            val itemMeta: ItemMeta = itemStack.itemMeta!!
            itemMeta.setDisplayName("${ChatColor.RED}Player Report!")
            val lore: MutableList<String> = ArrayList()
            lore.add("Reporter: " + report.reporter.name)
            lore.add("Reported: " + report.reported.name)
            lore.add("Reason: " + report.reason)
            lore.add("ID: " + report.id)
            itemMeta.lore = lore
            itemStack.setItemMeta(itemMeta)
            inventory.addItem(itemStack)
        }
        player.openInventory(inventory)
    }
}
