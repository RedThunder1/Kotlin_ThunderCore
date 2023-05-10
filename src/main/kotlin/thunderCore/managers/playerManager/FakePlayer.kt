package thunderCore.managers.playerManager

import org.bukkit.entity.Player
import java.util.UUID

data class FakePlayer(var rank: Ranks, val uuid: UUID, var subperms: List<String>?, val friends: ArrayList<Player>, var coins: Int, var muted: Boolean, var inGame: Boolean)
