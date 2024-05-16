package thunderCore.managers.floatingtextmanager

import com.google.gson.Gson
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import thunderCore.ThunderCore
import thunderCore.managers.ThunderManager
import thunderCore.managers.fileManager.FileManager
import java.io.File
import java.lang.NullPointerException
import java.util.*
import kotlin.collections.ArrayList

class FloatingTextManager: ThunderManager {

    //TODO:
    // Create support for multiple lines
    //     Do this by creating another stand below the previous one, and detect new lines with \n in the input
    // Properly implement saving and loading stands, current code should work but needs proper testing

    companion object { lateinit var get: FloatingTextManager }
    var stands = ArrayList<FloatingText>()
    private val gson = Gson()

    init { get = this }

    fun createFloatingText(player: Player, id: String, name: String) {

        //Replace ~ with mc color code symbol
        var customName = name.replace('~', '§', true)

        //replace ↓ (alt 2 5) with a new line by creating another stand below the previous one
        val subStands = ArrayList<ArmorStand>()
        var lastRange = 0
        var offset = 1
        for (i in customName.indices) {
            if (customName[i] == '↓') {
                if (lastRange == 0) {
                    customName = name.substring(lastRange, i)
                } else {
                    val stand: ArmorStand = player.world.spawnEntity(Location(player.world, player.location.x, player.location.y - (0.1 * offset), player.location.z)
                        , EntityType.ARMOR_STAND) as ArmorStand
                    stand.isVisible = false
                    stand.setGravity(false)
                    stand.customName = name.substring(lastRange, i)
                    stand.isCustomNameVisible = true
                    subStands.add(stand)
                }
                lastRange = i+1
                offset++
            }
        }


        val stand: ArmorStand = player.world.spawnEntity(player.location, EntityType.ARMOR_STAND) as ArmorStand
        stand.isVisible = false
        stand.setGravity(false)
        stand.customName = customName
        stand.isCustomNameVisible = true
        stands.add(FloatingText(id, player.location, stand, subStands))
        player.sendMessage("${ChatColor.GREEN}Holo has been created! ID: $id")
    }

    fun removeFloatingText(id: String): String {
        if (stands.isEmpty()) { return "${ChatColor.RED}There are no stands to remove!" }
        val stand = getStand(id) ?: return "${ChatColor.RED}That is not a stand!"
        stand.armorStand.remove()
        if (stand.subStands.isNotEmpty()) {
            for (s in stand.subStands) { s.remove() }
        }
        return "${ChatColor.GREEN}That stand has been removed!!"
    }

    fun getStand(id: String): FloatingText? {
        for (stand in stands) {
            if (stand.id == id) { return stand }
        }
        return null
    }

    override fun load() {
        try {
            val folder = File("ThunderCore/FloatingText/")
            val listOfFiles = folder.listFiles()
            for (file in Objects.requireNonNull<Array<File>>(listOfFiles)) {
                val fileContent: String? = FileManager.get.readFile(file)
                stands.add(gson.fromJson(fileContent, FloatingText::class.java))
            }
            for (stand in stands) {
                val s: ArmorStand = stand.location.world!!.spawnEntity(stand.location, EntityType.ARMOR_STAND) as ArmorStand
                s.isVisible = false
                s.setGravity(false)
                s.customName = stand.armorStand.customName
                s.isCustomNameVisible = true
                if (stand.subStands.isNotEmpty()) {
                    var offset = 1
                    val loc = stand.location
                    val newList = ArrayList<ArmorStand>()
                    for (subStand in stand.subStands) {
                        val sub: ArmorStand = loc.world!!.spawnEntity(Location(loc.world, loc.x, loc.y - (0.1 * offset), loc.z), EntityType.ARMOR_STAND) as ArmorStand
                        sub.isVisible = false
                        sub.setGravity(false)
                        sub.customName = subStand.customName
                        sub.isCustomNameVisible = true
                        newList.add(sub)
                        offset++
                    }
                    stand.subStands = newList
                }
            }
        } catch (e: NullPointerException) {
            ThunderCore.get.yellowMsg("THERE ARE NO FLOATING TEXT!")
        }
        ThunderCore.get.greenMsg("Floating Text loaded!")
    }

    override fun save() {
        if (stands.isNotEmpty()) {
            for (stand in stands) {
                val id: String = stand.id
                FileManager.get.writeFile(File("ThunderCore/FloatingText/$id.json"), gson.toJson(stands))
                removeFloatingText(stand.id)
            }
        }
        ThunderCore.get.greenMsg("Saved Floating Text!")
    }

}