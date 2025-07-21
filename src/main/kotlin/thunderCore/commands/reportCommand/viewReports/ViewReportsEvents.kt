package thunderCore.commands.reportCommand.viewReports

import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import thunderCore.managers.reportManager.ReportManager
import java.util.UUID

class ViewReportsEvents: Listener {

    @EventHandler
    fun onItemInteract(event: InventoryClickEvent) {
        if (ChatColor.stripColor(event.view.title) != "Reports Menu") { return }
        if (event.click.isRightClick) {
            try {
                var report = ReportManager.get.getReportByID(UUID.fromString(event.currentItem?.itemMeta?.lore?.get(3)?.drop(4)))
                if (report != null) { ReportManager.get.removeReport(report) }
            } catch (e: Exception) {}

        }
        event.isCancelled = true
    }

}