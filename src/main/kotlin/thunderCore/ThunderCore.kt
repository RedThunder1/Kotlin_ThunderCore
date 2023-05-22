package thunderCore

import org.bukkit.ChatColor
import org.bukkit.WorldCreator
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import thunderCore.commands.FriendsCommand
import thunderCore.commands.LobbyCommand
import thunderCore.commands.PartyCommand
import thunderCore.commands.kitpvp.KitPvpCommand
import thunderCore.commands.kitpvp.kitsCommand.KitsCommand
import thunderCore.commands.kitpvp.kitsCommand.KitsGUIEvent
import thunderCore.commands.staffCommands.*
import thunderCore.commands.staffCommands.buildCommand.BuildCommand
import thunderCore.commands.staffCommands.bypassCommand.BypassCommand
import thunderCore.commands.staffCommands.worlds.CreateWorldCommand
import thunderCore.commands.staffCommands.worlds.DeleteWorldCommand
import thunderCore.commands.staffCommands.worlds.TpWorldCommand
import thunderCore.commands.staffCommands.worlds.WorldListCommand
import thunderCore.events.ChatListener
import thunderCore.events.PlayerJoin
import thunderCore.events.PlayerLeave
import thunderCore.events.WorldProtection
import thunderCore.games.bedWarsManager.BedWarsManager
import thunderCore.games.kitpvpManager.KitPvPEvents
import thunderCore.games.kitpvpManager.KitPvPManager
import thunderCore.managers.FriendManager
import thunderCore.managers.ThunderManager
import thunderCore.managers.fileManager.FileManager
import thunderCore.managers.floatingtextmanager.FloatingTextManager
import thunderCore.managers.npcmanager.NPCManager
import thunderCore.managers.partyManager.PartyManager
import thunderCore.managers.playerManager.FakePlayer
import thunderCore.managers.playerManager.PlayerManager
import thunderCore.managers.reportManager.ReportManager
import thunderCore.utilities.AnnouncementMessages
import thunderCore.utilities.Time

class ThunderCore: JavaPlugin() {

    private var console = server.consoleSender
    private val thunderName: String = "" + ChatColor.YELLOW + "THUNDER" + ChatColor.AQUA + "MC" + ChatColor.RESET
    // This is why I hate components ^^^^

    companion object {
        lateinit var get: ThunderCore
    }


    private val managers: ArrayList<ThunderManager> = ArrayList()

    //TODO:
    // Priority:
    //      Kitpvp
    //          Make a penalty for leaving in combat
    //          More balanced kits
    //      Party system
    //          Have party members join games with leader
    //          Members can't start games
    // Secondary:
    //      Implement subperms into commands
    //      Test for bugs once a server is set up
    //      Other Games
    //          BedWars, Skywars, SkyBlock, Kitpvp, PartyGames like Tnt run
    //      Friend system
    //      Timed Mute
    //      SQL Database (Once I figure out how it works)
    // Bugs to fix:
    //      No known bugs at this time

    override fun onEnable() {
        get = this
        loadWorlds()
        loadManagers()
        loadEvents()
        loadRunnables()
        loadCommands()
        greenMsg("ENABLED!")
    }

    override fun onLoad() { greenMsg("LOADED!") }

    override fun onDisable() {
        //Delete game worlds
        for (thunderManager in managers) {
            thunderManager.save()
        }
        redMsg("DISABLED!")
    }

    private fun loadManagers() {
        managers.add(PlayerManager())
        managers.add(ReportManager())
        managers.add(FriendManager())
        managers.add(PartyManager())
        managers.add(FileManager())
        managers.add(KitPvPManager())
        managers.add(BedWarsManager())
        managers.add(NPCManager())
        managers.add(FloatingTextManager())

        greenMsg("Managers have been INITIALIZED")
        for (thunderManager in managers) {
            thunderManager.load()
        }
        greenMsg("Managers LOADED!")
    }

    private fun loadEvents() {
        val pluginManager = server.pluginManager
        pluginManager.registerEvents(KitPvPEvents(), this)
        pluginManager.registerEvents(KitsGUIEvent(), this)
        pluginManager.registerEvents(PlayerJoin(), this)
        pluginManager.registerEvents(PlayerLeave(), this)
        pluginManager.registerEvents(WorldProtection(), this)
        pluginManager.registerEvents(ChatListener(), this)
        greenMsg("Events LOADED!")
    }

    private fun loadRunnables() {
        val scheduler = server.scheduler
        scheduler.runTaskTimer(this, AnnouncementMessages(), 0, Time.TEN_MIN)
        greenMsg("Runnables LOADED!")
    }

    private fun loadWorlds() {
        val worlds = ArrayList<String>()
        worlds.add("lobby")
        worlds.add("lobbytemplate")
        worlds.add("kitpvp")
        worlds.add("building")
        greenMsg("Worlds Initialized!")
        for (world in worlds) {
            val worldCreator = WorldCreator(world)
            worldCreator.createWorld()
        }
        greenMsg("Worlds LOADED!")
    }

    private fun loadCommands() {
        getCommand("lobby")!!.setExecutor(LobbyCommand())
        getCommand("lobby")!!.setAliases(listOf("hub"))
        getCommand("ban")!!.setExecutor(BanCommand())
        getCommand("ban")!!.setAliases(listOf("ipban"))
        getCommand("mutechat")!!.setExecutor(MuteChatCommand())
        getCommand("vanish")!!.setExecutor(VanishCommand())
        getCommand("build")!!.setExecutor(BuildCommand())
        getCommand("mute")!!.setExecutor(MuteCommand())
        getCommand("mute")!!.setAliases(listOf("unmute"))
        getCommand("getvanished")!!.setExecutor(GetVanishedCommand())
        getCommand("party")!!.setExecutor(PartyCommand())
        getCommand("party")!!.setAliases(listOf("p"))
        getCommand("worldcreate")!!.setExecutor(CreateWorldCommand())
        getCommand("worldcreate")!!.setAliases(listOf("wc"))
        getCommand("worlddelete")!!.setExecutor(DeleteWorldCommand())
        getCommand("worlddelete")!!.setAliases(listOf("wd"))
        getCommand("worldtp")!!.setExecutor(TpWorldCommand())
        getCommand("worldtp")!!.setAliases(listOf("wtp"))
        getCommand("worldlist")!!.setExecutor(WorldListCommand())
        getCommand("worldlist")!!.setAliases(listOf("wl"))
        getCommand("setrank")!!.setExecutor(SetRankCommand())
        getCommand("sudo")!!.setExecutor(SudoCommand())
        getCommand("kits")!!.setExecutor(KitsCommand())
        getCommand("kitpvp")!!.setExecutor(KitPvpCommand())
        getCommand("bypass")!!.setExecutor(BypassCommand())
        getCommand("friend")!!.setExecutor(FriendsCommand())
        getCommand("friend")!!.setAliases(listOf("f"))
        getCommand("flyspeed")!!.setExecutor(FlySpeedCommand())
        getCommand("holo")!!.setExecutor(FloatingTextCommand())
        greenMsg("Commands LOADED!")
    }

    fun greenMsg(text: String) {
        console.sendMessage(thunderName + ChatColor.GREEN + ": $text")
    }

    fun redMsg(text: String) {
        console.sendMessage(thunderName + ChatColor.RED + ": $text")
    }

    fun yellowMsg(text: String) {
        console.sendMessage(thunderName + ChatColor.YELLOW + ": $text")
    }

    fun isStaff(player: Player): Boolean {
        return if (PlayerManager.get.fakePlayers.contains(PlayerManager.get.getFakePlayer(player))) {
            val fakePlayer: FakePlayer? = PlayerManager.get.getFakePlayer(player)
            fakePlayer!!.rank.permlevel >= 1
        } else player.isOp
    }

    fun isModerator(player: Player): Boolean {
        return if (PlayerManager.get.fakePlayers.contains(PlayerManager.get.getFakePlayer(player))) {
            val fakePlayer: FakePlayer? = PlayerManager.get.getFakePlayer(player)
            fakePlayer!!.rank.permlevel >= 2
        } else player.isOp
    }

    fun isAdmin(player: Player): Boolean {
        return if (PlayerManager.get.fakePlayers.contains(PlayerManager.get.getFakePlayer(player))) {
            val fakePlayer: FakePlayer? = PlayerManager.get.getFakePlayer(player)
            fakePlayer!!.rank.permlevel >= 3
        } else player.isOp
    }

    fun isOwner(player: Player): Boolean {
        return if (PlayerManager.get.fakePlayers.contains(PlayerManager.get.getFakePlayer(player))) {
            val fakePlayer: FakePlayer? = PlayerManager.get.getFakePlayer(player)
            fakePlayer!!.rank.permlevel >= 4
        } else player.isOp
    }

    fun isBuilder(player: Player): Boolean {
        return if (PlayerManager.get.fakePlayers.contains(PlayerManager.get.getFakePlayer(player))) {
            val fakePlayer: FakePlayer? = PlayerManager.get.getFakePlayer(player)
            fakePlayer!!.rank == PlayerManager.get.getRankByName("builder")
        } else isAdmin(player)
    }
}