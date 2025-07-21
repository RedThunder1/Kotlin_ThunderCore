package thunderCore.managers.playerManager

import java.util.ArrayList
import java.util.UUID

data class FakePlayer(val uuid: UUID, var rank: Ranks, var subperms: List<String>, val friends: ArrayList<String>, var coins: Int, var muted: Boolean, var inGame: Boolean)
