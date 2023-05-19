package thunderCore.utilities

import org.bukkit.ChatColor

class Messages {
    companion object {
        val NOPERMS: String = "" + ChatColor.RED + "You do not have permissions to use this command!"
        val CONSOLECANTUSE: String = "" + ChatColor.RED + "Console cant use this command!"
        val NOTAPLAYER: String = "" + ChatColor.RED + "That is not a Player!"
        val ERROR: String = "" + ChatColor.RED + "There was an error with this command!"

        val GAMES = "" + ChatColor.GOLD + "The games we currently have is the smp.  Other games are in the works!"
        val RULES = "" + ChatColor.RED + "Please make sure to read our rules in the discord server!"
        val WELCOME = "" + ChatColor.GREEN + "Welcome to ThunderMC! Here we will have many mini games like Smp, Duels, and Kitpvp."
    }
}