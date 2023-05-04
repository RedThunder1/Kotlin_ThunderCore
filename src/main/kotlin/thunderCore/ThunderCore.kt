package thunderCore

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.WorldCreator
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import thunderCore.commands.LobbyCommand
import thunderCore.commands.PartyCommand
import thunderCore.commands.staffCommands.*
import thunderCore.commands.staffCommands.buildCommand.BuildCommand
import thunderCore.commands.staffCommands.worlds.CreateWorldCommand
import thunderCore.commands.staffCommands.worlds.DeleteWorldCommand
import thunderCore.commands.staffCommands.worlds.TpWorldCommand
import thunderCore.events.ChatListener
import thunderCore.events.PlayerJoin
import thunderCore.events.PlayerLeave
import thunderCore.events.WorldProtection
import thunderCore.managers.ThunderManager
import thunderCore.managers.rankManager.FakePlayer
import thunderCore.managers.rankManager.RankManager
import thunderCore.utilities.AnnouncementMessages
import thunderCore.utilities.Time

object ThunderCore: JavaPlugin() {

    var console = server.consoleSender
    private val thunderName: TextComponent = Component.text().build()
        .decorate(TextDecoration.BOLD)
        .color { NamedTextColor.YELLOW.value() }
        .content("THUNDER")
        .color { NamedTextColor.AQUA.value() }
        .content("CORE")




    private lateinit var plugin: ThunderCore
    fun get(): ThunderCore { return plugin }

    private val managers: ArrayList<ThunderManager> = ArrayList()

    //TODO:
    // Priority:
    //      BedWars mini game
    //          Finalize managers
    //          Make temporary worlds for testing
    //      Commands
    //          Commands are mostly done I just need to do testing to ensure everything works correctly
    //      Party system
    //          Have party members join bedwars game with leader
    //          Members can't start games
    // Secondary:
    //      Replace ChatColor since its depreciated
    //          Test to make sure the new Component method works as intended
    //      Test for bugs once a server is set up
    //      Other Games
    //          Skywars, SkyBlock, Kitpvp, PartyGames like Tnt run
    //      Friend system
    //      Timed Mute
    // Bugs to fix:
    //      No know bugs at this time

    override fun onEnable() {
        plugin = this
        loadManagers()
        loadEvents()
        loadRunnables()
        loadWorlds()
        loadCommands()
        greenMsg("ENABLED!")
    }

    override fun onLoad() { greenMsg("LOADED!") }

    override fun onDisable() {
        for (thunderManager in managers) {
            thunderManager.save()
        }
        redMsg("DISABLED!")
    }

    private fun loadManagers() {
        managers.add(RankManager)
        greenMsg("Managers have been INITIALIZED")
        for (thunderManager in managers) {
            thunderManager.load()
        }
        greenMsg("Managers LOADED!")
    }

    private fun loadEvents() {
        val pluginManager = server.pluginManager
        pluginManager.registerEvents(PlayerJoin(), this)
        pluginManager.registerEvents(PlayerLeave(), this)
        pluginManager.registerEvents(WorldProtection(), this)
        pluginManager.registerEvents(ChatListener(), this)
        greenMsg("Events LOADED!")
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
        greenMsg("Worlds Initialized!")
        for (world in worlds) {
            val worldCreator = WorldCreator(world)
            worldCreator.createWorld()
        }
        greenMsg("Worlds LOADED!")
    }

    private fun loadCommands() {
        val banAlias = arrayOf("ipban")
        val muteAlias = arrayOf("unmute")
        val lobbyAlias = arrayOf("hub")
        val worldCreateAlias = arrayOf("wc")
        val worldDeleteAlias = arrayOf("wd")
        val worldTPAlias = arrayOf("wtp")
        getCommand("lobby")!!.setExecutor(LobbyCommand())
        getCommand("lobby")!!.setAliases(lobbyAlias.asList())
        getCommand("ban")!!.setExecutor(BanCommand())
        getCommand("ban")!!.setAliases(banAlias.asList())
        getCommand("mutechat")!!.setExecutor(MuteChatCommand())
        getCommand("vanish")!!.setExecutor(VanishCommand())
        getCommand("build")!!.setExecutor(BuildCommand())
        getCommand("mute")!!.setExecutor(MuteCommand())
        getCommand("mute")!!.setAliases(muteAlias.asList())
        getCommand("getvanished")!!.setExecutor(GetVanishedCommand())
        getCommand("party")!!.setExecutor(PartyCommand())
        getCommand("worldcreate")!!.setExecutor(CreateWorldCommand())
        getCommand("worldcreate")!!.setAliases(worldCreateAlias.asList())
        getCommand("worlddelete")!!.setExecutor(DeleteWorldCommand())
        getCommand("worlddelete")!!.setAliases(worldDeleteAlias.asList())
        getCommand("worldtp")!!.setExecutor(TpWorldCommand())
        getCommand("worldtp")!!.setAliases(worldTPAlias.asList())
        getCommand("setrank")!!.setExecutor(SetRankCommand())
        getCommand("sudo")!!.setExecutor(SudoCommand())

        greenMsg("Commands LOADED!")
    }

    fun greenMsg(text: String) {
        console.sendMessage(thunderName.append(Component.text(text).color(NamedTextColor.GREEN)))    }

    fun redMsg(text: String) {
        console.sendMessage(thunderName.append(Component.text(text).color(NamedTextColor.RED)))
    }

    fun yellowMsg(text: String) {
        console.sendMessage(thunderName.append(Component.text(text).color(NamedTextColor.YELLOW)))    }

    fun isStaff(player: Player): Boolean {
        return if (RankManager.get().fakePlayers.contains(RankManager.get().getFakePlayer(player))) {
            val fakePlayer: FakePlayer? = RankManager.get().getFakePlayer(player)
            fakePlayer!!.rank.permlevel >= 1
        } else player.isOp
    }

    fun isModerator(player: Player): Boolean {
        return if (RankManager.get().fakePlayers.contains(RankManager.get().getFakePlayer(player))) {
            val fakePlayer: FakePlayer? = RankManager.get().getFakePlayer(player)
            fakePlayer!!.rank.permlevel >= 2
        } else player.isOp
    }

    fun isAdmin(player: Player): Boolean {
        return if (RankManager.get().fakePlayers.contains(RankManager.get().getFakePlayer(player))) {
            val fakePlayer: FakePlayer? = RankManager.get().getFakePlayer(player)
            fakePlayer!!.rank.permlevel >= 3
        } else player.isOp
    }

    fun isOwner(player: Player): Boolean {
        return if (RankManager.get().fakePlayers.contains(RankManager.get().getFakePlayer(player))) {
            val fakePlayer: FakePlayer? = RankManager.get().getFakePlayer(player)
            fakePlayer!!.rank.permlevel >= 4
        } else player.isOp
    }

    fun isBuilder(player: Player): Boolean {
        return if (RankManager.get().fakePlayers.contains(RankManager.get().getFakePlayer(player))) {
            val fakePlayer: FakePlayer? = RankManager.get().getFakePlayer(player)
            fakePlayer!!.rank == RankManager.get().getRankByName("builder")
        } else isAdmin(player)
    }
}