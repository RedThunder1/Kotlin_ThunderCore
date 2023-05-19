package thunderCore.commands.kitpvp.kitsCommand

import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryMoveItemEvent
import thunderCore.games.kitpvpManager.KitData
import thunderCore.games.kitpvpManager.KitPvPManager

class KitsGUIEvent: Listener {

    @EventHandler
    fun playerInteractEvent(event: InventoryMoveItemEvent) { event.isCancelled = true }

    @EventHandler
    fun invClickEvent(event: InventoryClickEvent) {
        if (event.view.title != "${ChatColor.BOLD}${ChatColor.BLUE}Kits Menu") { return }

        val item = event.currentItem
        val player = event.whoClicked
        if (item == null) { return }

        var name = item.itemMeta!!.displayName
        name = name.substring(1, name.length-1).lowercase()
        val kit: KitData? = KitPvPManager.get.getKitByName(name)

        if (kit == null) {
            event.isCancelled = true
            return
        }
        player.inventory.clear()
        for (effect in player.activePotionEffects) { player.removePotionEffect(effect.type) }
        val equipment = player.equipment!!
        equipment.helmet = kit.armor[0]
        equipment.chestplate = kit.armor[1]
        equipment.leggings = kit.armor[2]
        equipment.boots = kit.armor[3]

        if (kit.effects != null) {
            for (effect in kit.effects) { player.addPotionEffect(effect) }
        }
        for (i in kit.items) { player.inventory.addItem(i) }

        player.closeInventory()
        event.isCancelled = true
    }

}