package thunderCore.utilities

import org.bukkit.ChatColor

class Messages {
    companion object {
        val NOPERMS: String = "" + ChatColor.RED + "You do not have permissions to use this command!"
        val CONSOLECANTUSE: String = "" + ChatColor.RED + "Console cant use this command!"
        val NOTAPLAYER: String = "" + ChatColor.RED + "That is not a Player!"
        val ERROR: String = "" + ChatColor.RED + "There was an error with this command!"
    }
}