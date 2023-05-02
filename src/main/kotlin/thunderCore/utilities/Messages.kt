package thunderCore.utilities

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.event.HoverEvent
import net.kyori.adventure.text.format.NamedTextColor

object Messages {

    val DISCORD = Component.text().build()
        .color(NamedTextColor.BLUE)
        .content("You can Join our discord at https://discord.gg/syVRwcn or by clicking here!")
        .clickEvent(ClickEvent.openUrl("https://discord.gg/syVRwcn"))
        .hoverEvent(HoverEvent.showText(Component.text("Join our discord!")))

    val NOPERMS = Component.text("You do not have permissions to use this command!").color(NamedTextColor.RED)
    val CONSOLECANTUSE = Component.text("Console cant use this command!").color(NamedTextColor.RED)
    val NOTAPLAYER = Component.text("That is not a Player!").color(NamedTextColor.RED)
    val ERROR = Component.text("There was an error with this command!").color(NamedTextColor.RED)


    val GAMES = Component.text("The games we currently have is the smp.  Other games are in the works!").color(NamedTextColor.GOLD)
    val RULES = Component.text("Please make sure to read our rules in the discord server!").color(NamedTextColor.RED)
    val WELCOME = Component.text("Welcome to ThunderMC! Here we will have many mini games like Smp, Duels, and Kitpvp.").color(NamedTextColor.GREEN)

}