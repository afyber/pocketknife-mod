package afyber.pocketknife.items;

import afyber.pocketknife.util.ItemUtil;
import com.google.common.collect.Multimap;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class ItemSwordCustom extends ItemSword {

	public ItemSwordCustom(ToolMaterial material) {
		super(material);
	}

	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
		Multimap<String, AttributeModifier> modifiers = super.getItemAttributeModifiers(slot);

		if (slot == EntityEquipmentSlot.MAINHAND) {
			ItemUtil.replaceAttributeModifier(modifiers, SharedMonsterAttributes.ATTACK_SPEED, ATTACK_SPEED_MODIFIER, -1.8);
			ItemUtil.replaceAttributeModifier(modifiers, SharedMonsterAttributes.ATTACK_DAMAGE, ATTACK_DAMAGE_MODIFIER, getAttackDamage() + 6.0);
		}

		return modifiers;
	}
}
