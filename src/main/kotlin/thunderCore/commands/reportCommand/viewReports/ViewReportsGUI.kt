package thunderCore.commands.reportCommand.viewReports

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import thunderCore.ThunderCore
import thunderCore.managers.reportManager.ReportManager

object ViewReportsGUI {
    fun reportsGui(player: Player) {
        val inventory: Inventory = ThunderCore.server.createInventory(null, 54, Component.text().build()
            .color(NamedTextColor.RED)
            .decorate(TextDecoration.BOLD)
            .content("Reports Menu"))
        for (report in ReportManager.reports) {
            val itemStack = ItemStack(Material.REDSTONE_BLOCK)
            val itemMeta: ItemMeta = itemStack.itemMeta
            itemMeta.displayName(Component.text("Player Report!", NamedTextColor.RED))
            val lore: MutableList<Component> = ArrayList()
            lore.add(Component.text("Reporter: " + report.reporter.name))
            lore.add(Component.text("Reported: " + report.reported.name))
            lore.add(Component.text("Reason: " + report.reason))
            lore.add(Component.text("ID: " + report.id))
            itemMeta.lore(lore)
            itemStack.setItemMeta(itemMeta)
            inventory.addItem(itemStack)
        }
        player.openInventory(inventory)
    }
}
