package thunderCore.managers.sqlmanager

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource
import com.mysql.cj.jdbc.MysqlDataSource
import org.bukkit.Bukkit
import thunderCore.ThunderCore
import thunderCore.managers.ThunderManager
import thunderCore.managers.playerManager.FakePlayer
import thunderCore.managers.playerManager.PlayerManager
import java.sql.Connection
import java.util.*


class SQLManager: ThunderManager {

    companion object {
        lateinit var get: SQLManager
    }

    private val dataSource: MysqlDataSource = MysqlConnectionPoolDataSource()
    private val host = "192.168.0.72"
    private val port = 3306
    private val database = "MC_Dev_Server"
    private val user = "core"
    private val password = pass().pass //ENV variables won't work for some reason so this will have to do for now;

    override fun load() {
        get = this
    }

    private fun connectToSQL(): Connection {
        dataSource.serverName = host
        dataSource.portNumber = port
        dataSource.databaseName = database
        dataSource.user = user
        dataSource.password = password
        return dataSource.connection
    }

    fun getFakePlayers() {
        try {
            connectToSQL()
            val conn = dataSource.connection
            val ps = conn.prepareStatement("SELECT * FROM `fakeplayers`")
            val rs = ps.executeQuery()
            while (rs.next()) {
                PlayerManager.get.fakePlayers.add(FakePlayer(
                    UUID.fromString(rs.getString(1)),
                    PlayerManager.get.getRankByName(rs.getString(2))!!,
                    rs.getString(3).split(","),
                    ArrayList<String>(rs.getString(4).split(",")), rs.getInt(5),
                    rs.getBoolean(6),
                    rs.getBoolean(7)))
            }
        } catch (e: Exception) {
            ThunderCore.get.redMsg("ERROR GETTING FAKE PLAYERS!")
            ThunderCore.get.redMsg("env: " + System.getenv())
            e.printStackTrace()
        }
    }

    fun saveFakePlayer(fakePlayer: FakePlayer) {
        try {
            connectToSQL()
            val conn = dataSource.connection
            val ps = conn.prepareStatement("REPlACE INTO fakeplayers(UUID, `Rank`, SubPerms, Friends, Coins, Muted, inGame) VALUES(?, ?, ?, ?, ?, ?, ?)")
            ps.setString(1 ,fakePlayer.uuid.toString())
            ps.setString(2, fakePlayer.rank.name)
            ps.setString(3, fakePlayer.subperms.toString())
            ps.setString(4, fakePlayer.friends.toString())
            ps.setInt(5, fakePlayer.coins)
            ps.setBoolean(6, fakePlayer.muted)
            ps.setBoolean(7, fakePlayer.inGame)
            ps.execute()
        } catch (e: Exception) {
            ThunderCore.get.redMsg("THERE WAS AN ERROR SAVING PLAYER!")
            e.printStackTrace()
        }
    }
}