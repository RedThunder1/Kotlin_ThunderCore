package thunderCore.managers.playerManager

import org.bukkit.ChatColor
import org.bukkit.entity.Player
import thunderCore.ThunderCore
import thunderCore.managers.ThunderManager
import thunderCore.managers.sqlmanager.SQLManager
import java.util.*
import kotlin.collections.ArrayList

class PlayerManager: ThunderManager {
    private val playerRanks: ArrayList<Ranks> = ArrayList()
    var fakePlayers: ArrayList<FakePlayer> = ArrayList()
    private val subPerms: ArrayList<String> = ArrayList()

    companion object {
        lateinit var get: PlayerManager
    }

    init {
        get = this
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

    fun createFakePlayer(player: Player, rank: String?, subperms: List<String>) {
        fakePlayers.add(FakePlayer(player.uniqueId, getRankByName(rank)!!, subperms , ArrayList(), 0, muted = false, inGame = false))
        ThunderCore.get.greenMsg("Created a fake player!")
    }

    fun checkSubPerm(string: String): Boolean {
        for (subPerm in subPerms) {
            if (string == subPerm) {
                return true
            }
        }
        return false
    }

    fun getRankColor(rank: Ranks): ChatColor {
        return when (rank.name) {
            "owner" -> { ChatColor.DARK_RED }
            "co-owner" -> { ChatColor.RED }
            "admin" -> { ChatColor.GOLD }
            "mod" -> { ChatColor.YELLOW }
            "trial-mod" -> { ChatColor.BLUE }
            "dev", "builder" -> { ChatColor.DARK_BLUE }
            "spartan" -> { ChatColor.GREEN }
            "member" -> { ChatColor.AQUA }
            else -> { ChatColor.WHITE }
        }
    }

    override fun load() {
        try {
            SQLManager.get.getFakePlayers()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        ThunderCore.get.greenMsg("Ranks loaded!")
    }

    override fun save() {
        for (fakeplayer in fakePlayers) {
            SQLManager.get.saveFakePlayer(fakeplayer)
        }
        ThunderCore.get.greenMsg("Saved Player Ranks!")
    }
}
