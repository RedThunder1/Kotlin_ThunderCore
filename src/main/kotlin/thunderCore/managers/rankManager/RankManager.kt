package thunderCore.managers.rankManager

import com.google.gson.Gson
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.entity.Player
import thunderCore.ThunderCore
import thunderCore.managers.ThunderManager
import thunderCore.managers.fileManager.FileManager
import java.io.File
import java.lang.NullPointerException
import java.util.*
import kotlin.collections.ArrayList


object RankManager: ThunderManager {
    private val gson = Gson()
    private val playerRanks: ArrayList<Ranks> = ArrayList()
    var fakePlayers: ArrayList<FakePlayer> = ArrayList()
    private val subPerms: ArrayList<String> = ArrayList()
    private var rankManager: RankManager = this
    fun get(): RankManager {
        return rankManager
    }


    init {
        val ownerPrefix = Component.text().build().decorate(TextDecoration.BOLD).color(NamedTextColor.DARK_RED).content("[Owner] ")
        playerRanks.add(Ranks("owner", 4, ownerPrefix))

        val coownerPrefix = Component.text().build().decorate(TextDecoration.BOLD).color(NamedTextColor.RED).content("[Co Owner] ")
        playerRanks.add(Ranks("co-owner", 4, coownerPrefix))

        val devPrefix = Component.text().build().decorate(TextDecoration.BOLD).color(NamedTextColor.DARK_BLUE).content("[Dev] ")
        playerRanks.add(Ranks("developer", 3, devPrefix))

        val adminPrefix = Component.text().build().decorate(TextDecoration.BOLD).color(NamedTextColor.GOLD).content("[Admin] ")
        playerRanks.add(Ranks("admin", 3, adminPrefix))

        val modPrefix = Component.text().build().decorate(TextDecoration.BOLD).color(NamedTextColor.YELLOW).content("[Mod] ")
        playerRanks.add(Ranks("mod", 2, modPrefix))

        val builderPrefix = Component.text().build().decorate(TextDecoration.BOLD).color(NamedTextColor.BLUE).content("[Builder] ")
        playerRanks.add(Ranks("builder", 2, builderPrefix))

        val tModPrefix = Component.text().build().decorate(TextDecoration.BOLD).color(NamedTextColor.BLUE).content("[Trial Mod] ")
        playerRanks.add(Ranks("trial-mod", 1, tModPrefix))

        val spartanPrefix = Component.text().build().decorate(TextDecoration.BOLD).color(NamedTextColor.GREEN).content("[Spartan] ")
        playerRanks.add(Ranks("spartan", 0, spartanPrefix))

        val memberPrefix = Component.text().build().decorate(TextDecoration.BOLD).color(NamedTextColor.AQUA).content("[Member] ")
        playerRanks.add(Ranks("member", 0, memberPrefix))

        subPerms.add("build")
        subPerms.add("heal")
        subPerms.add("mute")
        subPerms.add("vanish")
        subPerms.add("unmute")
        subPerms.add("kick")
        subPerms.add("ban")
        subPerms.add("unban")
        subPerms.add("worldtp")
        subPerms.add("mutechat")
        subPerms.add("sudo")
        subPerms.add("skullgive")
        subPerms.add("createworld")
        subPerms.add("deleteworld")
        subPerms.add("worldlist")
        subPerms.add("stop")
        subPerms.add("restart")
        subPerms.add("setrank")
        subPerms.add("setsubperms")
    }

    fun getPlayerRanks(): ArrayList<Ranks> { return playerRanks }

    fun getRankByName(name: String?): Ranks? {
        for (pRanks in playerRanks) {
            if (pRanks.name == name) {
                return pRanks
            }
        }
        return null
    }

    fun getPlayerRank(player: Player): Ranks? {
        for (fakePlayer in fakePlayers) {
            if (fakePlayer.uuid == player.uniqueId) {
                return fakePlayer.rank
            }
        }
        return null
    }

    fun getFakePlayer(player: Player): FakePlayer? {
        for (fakePlayer in fakePlayers) {
            if (fakePlayer.uuid == player.uniqueId) {
                return fakePlayer
            }
        }
        return null
    }

    fun createFakePlayer(player: Player, rank: String?, subperms: List<String>?) {
        ThunderCore.get().greenMsg("Created a fake player!")
        fakePlayers.add(FakePlayer(getRankByName(rank)!!, player.uniqueId, subperms, muted = false, inGame = false))
    }

    fun checkSubPerm(s: String): Boolean {
        for (subPerm in subPerms) {
            if (s == subPerm) {
                return true
            }
        }
        return false
    }

    override fun load() {
        try {
            val folder = File("ThunderCore/FakePlayers/")
            val listOfFiles = folder.listFiles()
            for (file in Objects.requireNonNull<Array<File>>(listOfFiles)) {
                val fileContent: String? = FileManager.readFile(file)
                fakePlayers.add(gson.fromJson(fileContent, FakePlayer::class.java))
            }
        } catch (e: NullPointerException) {
            ThunderCore.get().yellowMsg("THERE ARE NO PLAYER FILES!")
        }
        ThunderCore.get().greenMsg("Ranks loaded!")
    }

    override fun save() {
        for (fakePlayer in fakePlayers) {
            val id: String = fakePlayer.uuid.toString()
            FileManager.writeFile(File("ThunderCore/FakePlayers/$id.json"), gson.toJson(fakePlayer))
        }
        ThunderCore.get().greenMsg("Saved Player Ranks!")
    }
}
