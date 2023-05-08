package thunderCore.managers.rankManager

import com.google.gson.Gson
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
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
        val plainSerializer = PlainTextComponentSerializer.plainText()
        val ownerPrefix = "[Owner] "
        playerRanks.add(Ranks("owner", 4, ownerPrefix))

        val coownerPrefix = "[Co Owner] "
        playerRanks.add(Ranks("co-owner", 4, coownerPrefix))

        val devPrefix = "[Dev] "
        playerRanks.add(Ranks("developer", 3, devPrefix))

        val adminPrefix = "[Admin] "
        playerRanks.add(Ranks("admin", 3, adminPrefix))

        val modPrefix = "[Mod] "
        playerRanks.add(Ranks("mod", 2, modPrefix))

        val builderPrefix = "[Builder] "
        playerRanks.add(Ranks("builder", 2, builderPrefix))

        val tModPrefix = "[Trial Mod] "
        playerRanks.add(Ranks("trial-mod", 1, tModPrefix))

        val spartanPrefix = "[Spartan] "
        playerRanks.add(Ranks("spartan", 0, spartanPrefix))

        val memberPrefix = "[Member] "
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
        ThunderCore.get.greenMsg("Created a fake player!")
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

    fun getRankColor(rank: Ranks): NamedTextColor? {
        return when (rank.name) {
            "owner" -> { NamedTextColor.DARK_RED }
            "co-owner" -> { NamedTextColor.RED }
            "admin" -> { NamedTextColor.GOLD }
            "mod" -> { NamedTextColor.YELLOW }
            "trial-mod" -> { NamedTextColor.BLUE }
            "dev", "builder" -> { NamedTextColor.DARK_BLUE }
            "spartan" -> { NamedTextColor.GREEN }
            "member" -> { NamedTextColor.AQUA }
            else -> { NamedTextColor.WHITE }
        }
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
            ThunderCore.get.yellowMsg("THERE ARE NO PLAYER FILES!")
        }
        ThunderCore.get.greenMsg("Ranks loaded!")
    }

    override fun save() {
        for (fakePlayer in fakePlayers) {
            val id: String = fakePlayer.uuid.toString()
            FileManager.writeFile(File("ThunderCore/FakePlayers/$id.json"), gson.toJson(fakePlayer))
        }
        ThunderCore.get.greenMsg("Saved Player Ranks!")
    }
}
