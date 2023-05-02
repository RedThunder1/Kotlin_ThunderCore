package thunderCore.utilities

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.event.HoverEvent
import net.kyori.adventure.text.format.NamedTextColor
import thunderCore.ThunderCore

class AnnouncementMessages: Runnable{

    private val games = Component.text().build()
        .color(NamedTextColor.GOLD)
        .content("The current planned game is Bed Wars.  Once finished, other games will be developed.")

    private val rules = Component.text().build()
        .color(NamedTextColor.RED)
        .content("Please make sure to read our rules in the discord server!")

    private val welcome = Component.text().build()
        .color(NamedTextColor.GREEN)
        .content("Welcome to ThunderMC! Here we will have many mini games such as Bed Wars, Sky Wars, and others soon to come!")

    private val discord = Component.text().build()
        .color(NamedTextColor.BLUE)
        .content("You can Join our discord at https://discord.gg/syVRwcn or by clicking here!")
        .clickEvent(ClickEvent.openUrl("https://discord.gg/syVRwcn"))
        .hoverEvent(HoverEvent.showText(Component.text("Join our discord!")))

    private var messageNumber = 0
    override fun run() {
        messageNumber++
        when (messageNumber) {
            1 -> ThunderCore.server.broadcast(discord)
            2 -> ThunderCore.server.broadcast(rules)
            3 -> ThunderCore.server.broadcast(games)
            4 -> {
                ThunderCore.server.broadcast(welcome)
                messageNumber = 0
            }
            else -> messageNumber = 0
        }
    }
}