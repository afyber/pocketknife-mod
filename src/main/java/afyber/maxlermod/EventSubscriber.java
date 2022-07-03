package afyber.maxlermod;

import afyber.maxlermod.items.ItemBowCustom;
import afyber.maxlermod.items.ItemHolder;
import afyber.maxlermod.items.ToolMaterialCustom;
import afyber.maxlermod.util.ItemRegistrationHelper;
import appeng.api.features.InscriberProcessType;
import micdoodle8.mods.galacticraft.api.recipe.CompressorRecipes;
import micdoodle8.mods.galacticraft.core.GCItems;
import micdoodle8.mods.galacticraft.planets.asteroids.items.AsteroidsItems;
import micdoodle8.mods.galacticraft.planets.mars.items.MarsItems;
import micdoodle8.mods.galacticraft.planets.venus.VenusItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = MaxlerMod.MOD_ID)
public final class EventSubscriber {
	private EventSubscriber() {}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> registry = event.getRegistry();
		registry.register(ItemRegistrationHelper.newBasicItem("adamantite_ingot").setCreativeTab(CreativeTabs.MATERIALS));
		registry.register(ItemRegistrationHelper.newBasicItem("crystal_core").setCreativeTab(CreativeTabs.MATERIALS));
		registry.register(ItemRegistrationHelper.newBasicItem("crystal").setCreativeTab(CreativeTabs.MATERIALS));
		registry.register(ItemRegistrationHelper.newBasicItem("crystal_component").setCreativeTab(CreativeTabs.MATERIALS));
		registry.register(ItemRegistrationHelper.newBasicItem("inscribed_crystal_component").setCreativeTab(CreativeTabs.MATERIALS));
		registry.register(ItemRegistrationHelper.newItem(new ItemBowCustom(640, 4), "crystalline_bow").setCreativeTab(CreativeTabs.COMBAT));
		ItemRegistrationHelper.registerNewToolSet(registry, "adamantite", ToolMaterialCustom.ADAMANTITE);
		ItemRegistrationHelper.registerNewToolSet(registry, "crystalline", ToolMaterialCustom.CRYSTALLINE);
	}

	@SubscribeEvent
	public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
		IForgeRegistry<IRecipe> registry = event.getRegistry();
		if (MaxlerConfig.expensiveCrystalRecipe) {
			CompressorRecipes.addRecipe(new ItemStack(ItemHolder.CRYSTAL), "YYY", "YXY", "YYY", 'X', new ItemStack(ItemHolder.CRYSTAL_CORE), 'Y', new ItemStack(Items.DIAMOND));
		}
		else {
			CompressorRecipes.addRecipe(new ItemStack(ItemHolder.CRYSTAL), " Y ", "YXY", " Y ", 'X', new ItemStack(ItemHolder.CRYSTAL_CORE), 'Y', new ItemStack(Items.DIAMOND));
		}
		CompressorRecipes.addRecipe(new ItemStack(ItemHolder.ADAMANTITE_INGOT), "XYB", "ZAZ", "BYX", 'X', new ItemStack(AsteroidsItems.basicItem, 1, 6), 'Y', new ItemStack(VenusItems.basicItem, 1, 1), 'Z', new ItemStack(MarsItems.marsItemBasic, 1, 2), 'A', new ItemStack(Items.DIAMOND), 'B', new ItemStack(GCItems.basicItem, 1, 10));
		AE2Api.registerInscriberRecipe(InscriberProcessType.INSCRIBE, new ItemStack(ItemHolder.INSCRIBED_CRYSTAL_COMPONENT), AE2Api.getItemStack(AE2Api.getAE2Materials().engProcessorPrint(), 1), AE2Api.getItemStack(AE2Api.getAE2Materials().siliconPrint(), 1), new ItemStack(ItemHolder.CRYSTAL_COMPONENT));
	}
}
