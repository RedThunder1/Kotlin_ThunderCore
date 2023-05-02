package thunderCore

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import thunderCore.managers.ThunderManager
import thunderCore.managers.rankManager.FakePlayer
import thunderCore.managers.rankManager.RankManager

object ThunderCore  : JavaPlugin() {

    private var console = server.consoleSender
    private val thunderName: TextComponent = Component.text().build()
        .decorate(TextDecoration.BOLD)
        .color { NamedTextColor.YELLOW.value() }
        .content("THUNDER")
        .color { NamedTextColor.AQUA.value() }
        .content("CORE")




    private lateinit var plugin: ThunderCore
    fun get(): ThunderCore {
        return plugin
    }

    private val managers: ArrayList<ThunderManager> = ArrayList()

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



        greenMsg("Events LOADED!")
    }

    private fun loadRunnables() {



        greenMsg("Runnables LOADED!")
    }

    private fun loadWorlds() {



        greenMsg("Worlds LOADED!")
    }

    private fun loadCommands() {
        /*
        val banAlias = arrayOf("ipban")
        val muteAlias = arrayOf("unmute")
        val lobbyAlias = arrayOf("hub")
        val worldCreateAlias = arrayOf("wc")
        val worldDeleteAlias = arrayOf("wd")
        val worldTPAlias = arrayOf("wtp")
        Objects.requireNonNull<PluginCommand?>(getCommand("lobby")).setExecutor(LobbyCommand())
        Objects.requireNonNull(getCommand("lobby")).setAliases(List.of(*lobbyAlias))
        Objects.requireNonNull<PluginCommand?>(getCommand("ban")).setExecutor(BanCommand())
        Objects.requireNonNull(getCommand("ban")).setAliases(List.of(*banAlias))
        Objects.requireNonNull<PluginCommand?>(getCommand("mutechat")).setExecutor(MuteChatCommand())
        Objects.requireNonNull<PluginCommand?>(getCommand("vanish")).setExecutor(VanishCommand())
        Objects.requireNonNull<PluginCommand?>(getCommand("build")).setExecutor(BuildCommand())
        Objects.requireNonNull<PluginCommand?>(getCommand("mute")).setExecutor(MuteCommand())
        Objects.requireNonNull(getCommand("mute")).setAliases(List.of(*muteAlias))
        Objects.requireNonNull<PluginCommand?>(getCommand("getvanished")).setExecutor(GetVanishedCommand())
        Objects.requireNonNull<PluginCommand?>(getCommand("party")).setExecutor(PartyCommand())
        Objects.requireNonNull<PluginCommand?>(getCommand("worldcreate")).setExecutor(CreateWorldCommand())
        Objects.requireNonNull(getCommand("worldcreate")).setAliases(List.of(*worldCreateAlias))
        Objects.requireNonNull<PluginCommand?>(getCommand("worlddelete")).setExecutor(DeleteWorldCommand())
        Objects.requireNonNull(getCommand("worlddelete")).setAliases(List.of(*worldDeleteAlias))
        Objects.requireNonNull<PluginCommand?>(getCommand("worldtp")).setExecutor(TpWorldCommand())
        Objects.requireNonNull(getCommand("worldtp")).setAliases(List.of(*worldTPAlias))
        Objects.requireNonNull<PluginCommand?>(getCommand("setrank")).setExecutor(SetRankCommand())
        Objects.requireNonNull<PluginCommand?>(getCommand("sudo")).setExecutor(SudoCommand())
         */
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