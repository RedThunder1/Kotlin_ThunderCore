package thunderCore.managers.reportManager

import org.bukkit.entity.Player
import java.util.*

data class ReportRecord(val reporter: Player, val reported: Player, val reason: String, val id: UUID)
