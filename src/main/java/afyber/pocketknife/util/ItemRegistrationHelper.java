package afyber.pocketknife.util;

import afyber.pocketknife.Pocketknife;
import afyber.pocketknife.items.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public final class ItemRegistrationHelper {
	private ItemRegistrationHelper() {}

	public static Item newBasicItem(String itemName) {
		return newItem(new Item(), itemName);
	}

	public static Item newItem(Item item, String itemName) {
		Item newI = item.setRegistryName(Pocketknife.MOD_ID, itemName).setUnlocalizedName(Pocketknife.MOD_ID + "." + itemName);
		PocketknifeItems.allItems().add(newI);
		return newI;
	}

	public static void registerNewToolSet(IForgeRegistry<Item> registry, String materialName, Item.ToolMaterial material) {
		registry.register(newItem(new ItemSwordCustom(material), materialName + "_sword").setCreativeTab(CreativeTabs.COMBAT));
		registry.register(newItem(new ItemPickaxeCustom(material), materialName + "_pickaxe").setCreativeTab(CreativeTabs.TOOLS));
		registry.register(newItem(new ItemAxeCustom(material), materialName + "_axe").setCreativeTab(CreativeTabs.TOOLS));
		registry.register(newItem(new ItemShovelCustom(material), materialName + "_shovel").setCreativeTab(CreativeTabs.TOOLS));
		registry.register(newItem(new ItemHoeCustom(material), materialName + "_hoe").setCreativeTab(CreativeTabs.TOOLS));
	}
}
