package afyber.maxlermod.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

public class HomingEnchantment extends Enchantment {

	 public HomingEnchantment() {
		 super(Rarity.VERY_RARE, EnumEnchantmentType.BOW, new EntityEquipmentSlot[]{ EntityEquipmentSlot.MAINHAND });
		 this.setName("homing");
	 }
}
