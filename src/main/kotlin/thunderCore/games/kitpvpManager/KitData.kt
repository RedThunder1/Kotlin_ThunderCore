package thunderCore.games.kitpvpManager

import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect

data class KitData(val name: String,val armor: List<ItemStack> , val items: List<ItemStack>, val logo: ItemStack, val effects: List<PotionEffect>?)
