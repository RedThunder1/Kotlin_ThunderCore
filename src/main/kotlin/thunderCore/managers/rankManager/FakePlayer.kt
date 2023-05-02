package thunderCore.managers.rankManager

import java.util.UUID

data class FakePlayer(var rank: Ranks, val uuid: UUID, var subperms: List<String>?, var muted: Boolean, var inGame: Boolean)
