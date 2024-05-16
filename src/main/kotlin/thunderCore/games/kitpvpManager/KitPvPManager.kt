package thunderCore.games.kitpvpManager

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import thunderCore.managers.ThunderManager
import kotlin.collections.ArrayList

class KitPvPManager: ThunderManager {
    companion object {
        lateinit var get: KitPvPManager
    }

    var kits: ArrayList<KitData> = ArrayList()
    var players = ArrayList<Player>()
    private val spawn = Location(Bukkit.getWorld("kitpvp"), -6.5, 136.0, -54.5)

    init {
        get = this
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

        val brawlerEffects = ArrayList<PotionEffect>()
        brawlerEffects.add(PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1))

        val brawlerLogo = ItemStack(Material.IRON_SWORD)
        val brawlerLogoMeta = brawlerLogo.itemMeta
        brawlerLogoMeta!!.setDisplayName("" + ChatColor.AQUA + "Brawler")
        brawlerLogo.itemMeta = brawlerLogoMeta
        kits.add(KitData("brawler", brawlerArmor, brawlerKit, brawlerLogo, brawlerEffects))



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
        fighterLogoMeta!!.setDisplayName("" + ChatColor.AQUA + "Fighter")
        fighterLogo.itemMeta = fighterLogoMeta
        kits.add(KitData("fighter", fighterArmor, fighterKit, fighterLogo, null))


        
        //Tank Kit
        val tankArmor = ArrayList<ItemStack>()
        tankArmor.add(ItemStack(Material.DIAMOND_HELMET))
        tankArmor.add(ItemStack(Material.DIAMOND_CHESTPLATE))
        tankArmor.add(ItemStack(Material.DIAMOND_LEGGINGS))
        tankArmor.add(ItemStack(Material.DIAMOND_BOOTS))

        val tankKit = ArrayList<ItemStack>()
        tankKit.add(ItemStack(Material.IRON_SWORD))
        tankKit.add(ItemStack(Material.COOKED_BEEF, 16))

        val tankEffects = ArrayList<PotionEffect>()
        tankEffects.add(PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 1))

        val tankLogo = ItemStack(Material.DIAMOND_CHESTPLATE)
        val tankLogoMeta = tankLogo.itemMeta
        tankLogoMeta!!.setDisplayName("" + ChatColor.AQUA + "Tank")
        tankLogo.itemMeta = tankLogoMeta
        kits.add(KitData("tank", tankArmor, tankKit, tankLogo, tankEffects))



        //Archer kit
        val archerArmor = ArrayList<ItemStack>()
        archerArmor.add(ItemStack(Material.IRON_HELMET))
        archerArmor.add(ItemStack(Material.IRON_CHESTPLATE))
        archerArmor.add(ItemStack(Material.CHAINMAIL_LEGGINGS))
        archerArmor.add(ItemStack(Material.IRON_BOOTS))

        val archerKit = ArrayList<ItemStack>()
        archerKit.add(ItemStack(Material.IRON_SWORD))
        archerKit.add(ItemStack(Material.BOW))
        archerKit.add(ItemStack(Material.ARROW, 64))
        archerKit.add(ItemStack(Material.COOKED_BEEF, 16))

        val archerLogo = ItemStack(Material.BOW)
        val archerLogoMeta = archerLogo.itemMeta
        archerLogoMeta!!.setDisplayName("" + ChatColor.AQUA + "Archer")
        archerLogo.itemMeta = archerLogoMeta
        kits.add(KitData("archer", archerArmor, archerKit, archerLogo, null))
    }

    fun playerJoin(player: Player) {
        players.add(player)
        player.teleport(spawn)
    }

    fun getKitByName(name: String): KitData? {
        for (kit in kits) {
            if (ChatColor.stripColor(kit.name) == ChatColor.stripColor(name)) { return kit }
        }
        return null
    }
}