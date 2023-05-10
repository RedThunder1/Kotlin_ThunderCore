package thunderCore.commands.kitpvp.kitsCommand

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
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
        if (event.view.title() != Component.text().build()
        .color(NamedTextColor.BLUE)
        .decorate(TextDecoration.BOLD)
        .content("Kits Menu")) { return }

        val item = event.currentItem
        val player = event.whoClicked
        if (item == null) {
            return
        }

        val plainSerializer = PlainTextComponentSerializer.plainText()
        val name = plainSerializer.serialize(item.displayName())
        val kit: KitData? = KitPvPManager.getKitByName(name)

        if (kit == null) {
            event.isCancelled = true
            return
        }

        val equipment = player.equipment
        equipment.helmet = kit.armor[0]
        equipment.chestplate = kit.armor[1]
        equipment.leggings = kit.armor[2]
        equipment.boots = kit.armor[3]

        for (i in kit.items) { player.inventory.addItem(i) }

        event.inventory.close()
        event.isCancelled = true
    }

}