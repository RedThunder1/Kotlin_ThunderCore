package thunderCore.managers.floatingtextmanager

import org.bukkit.Location
import org.bukkit.entity.ArmorStand

data class FloatingText(val id: String, var location: Location, val stand: ArmorStand, val subStands: ArrayList<ArmorStand>)
