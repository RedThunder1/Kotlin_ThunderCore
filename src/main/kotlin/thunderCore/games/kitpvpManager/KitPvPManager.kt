package thunderCore.games.kitpvpManager

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import kotlin.collections.ArrayList


object KitPvPManager {

    var kits: ArrayList<KitData> = ArrayList()
    var players = ArrayList<Player>()
    private val spawn = Location(Bukkit.getWorld("kitpvp"), 0.5, 72.0, 0.5)

    init {
        //These are the kits for now.  They will be balanced soon, and I'll add more kits soon.
        //kits will also be unlockable in the future.  Whether that's by leveling up or earning and spending coins in game I don't know yet

        //Brawler Kit
        val brawlerArmor = ArrayList<ItemStack>()
        brawlerArmor.add(ItemStack(Material.IRON_HELMET))
        brawlerArmor.add(ItemStack(Material.IRON_CHESTPLATE))
        brawlerArmor.add(ItemStack(Material.IRON_LEGGINGS))
        brawlerArmor.add(ItemStack(Material.IRON_BOOTS))

        val brawlerKit = ArrayList<ItemStack>()
        brawlerKit.add(ItemStack(Material.IRON_SWORD))
        brawlerKit.add(ItemStack(Material.COOKED_BEEF, 16))

        val brawlerLogo = ItemStack(Material.IRON_SWORD)
        val brawlerLogoMeta = brawlerLogo.itemMeta
        brawlerLogoMeta.displayName(Component.text("Brawler", NamedTextColor.AQUA))
        brawlerLogo.itemMeta = brawlerLogoMeta
        kits.add(KitData("brawler", brawlerArmor, brawlerKit, brawlerLogo))

        //Fighter Kit
        val fighterArmor = ArrayList<ItemStack>()
        fighterArmor.add(ItemStack(Material.DIAMOND_HELMET))
        fighterArmor.add(ItemStack(Material.IRON_CHESTPLATE))
        fighterArmor.add(ItemStack(Material.IRON_LEGGINGS))
        fighterArmor.add(ItemStack(Material.IRON_BOOTS))

        val fighterKit = ArrayList<ItemStack>()
        fighterKit.add(ItemStack(Material.DIAMOND_SWORD))
        fighterKit.add(ItemStack(Material.COOKED_BEEF, 16))

        val fighterLogo = ItemStack(Material.IRON_CHESTPLATE)
        val fighterLogoMeta = fighterLogo.itemMeta
        fighterLogoMeta.displayName(Component.text("Brawler", NamedTextColor.AQUA))
        fighterLogo.itemMeta = fighterLogoMeta
        kits.add(KitData("fighter", fighterArmor, fighterKit, fighterLogo))

        //Tank Kit
        val tankArmor = ArrayList<ItemStack>()
        tankArmor.add(ItemStack(Material.DIAMOND_HELMET))
        tankArmor.add(ItemStack(Material.DIAMOND_CHESTPLATE))
        tankArmor.add(ItemStack(Material.DIAMOND_LEGGINGS))
        tankArmor.add(ItemStack(Material.DIAMOND_BOOTS))

        val tankKit = ArrayList<ItemStack>()
        tankKit.add(ItemStack(Material.IRON_SWORD))
        tankKit.add(ItemStack(Material.COOKED_BEEF, 16))

        val tankLogo = ItemStack(Material.DIAMOND_CHESTPLATE)
        val tankLogoMeta = tankLogo.itemMeta
        tankLogoMeta.displayName(Component.text("Brawler", NamedTextColor.AQUA))
        tankLogo.itemMeta = tankLogoMeta
        kits.add(KitData("tank", tankArmor, tankKit, tankLogo))
    }

    fun playerJoin(player: Player) {
        players.add(player)
        player.teleport(spawn)
    }

    fun getKitByName(name: String): KitData? {
        for (kit in kits) {
            if (name == kit.name) {
                return kit
            }
        }
        return null
    }
}