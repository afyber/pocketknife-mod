package afyber.maxlermod.items;

import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemBow;

public class ItemBowCustom extends ItemBow {

	private final float bonusDamage;

	public ItemBowCustom(int maxDamage, float bonusDamage) {
		super();
		this.bonusDamage = bonusDamage;
		this.setMaxDamage(maxDamage);
	}

	@Override
	public EntityArrow customizeArrow(EntityArrow arrow) {
		arrow.setDamage(arrow.getDamage() + bonusDamage);
		return arrow;
	}
}
