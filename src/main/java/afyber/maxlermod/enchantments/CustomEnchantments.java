package afyber.maxlermod.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class CustomEnchantments {
	private CustomEnchantments() {}

	public static final Enchantment HOMING = getRegisteredEnchantment("maxlermod:homing");

	@Nullable
	private static Enchantment getRegisteredEnchantment(String id)
	{
		Enchantment enchantment = Enchantment.REGISTRY.getObject(new ResourceLocation(id));

		if (enchantment == null)
		{
			throw new IllegalStateException("Invalid Enchantment requested: " + id);
		}
		else
		{
			return enchantment;
		}
	}
}
