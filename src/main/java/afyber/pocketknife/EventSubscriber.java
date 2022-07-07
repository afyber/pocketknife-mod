package afyber.pocketknife;

import afyber.pocketknife.items.ItemBowCustom;
import afyber.pocketknife.items.ItemHolder;
import afyber.pocketknife.items.ToolMaterialCustom;
import afyber.pocketknife.util.ItemRegistrationHelper;
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
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryModifiable;

@Mod.EventBusSubscriber(modid = Pocketknife.MOD_ID)
public final class EventSubscriber {
	private EventSubscriber() {}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> registry = event.getRegistry();
		if (PocketknifeConfig.hasAdamantiteMaterial) {
			registry.register(ItemRegistrationHelper.newBasicItem("adamantite_ingot").setCreativeTab(CreativeTabs.MATERIALS));
		}
		if (PocketknifeConfig.hasCrystallineMaterial) {
			registry.register(ItemRegistrationHelper.newBasicItem("crystal_core").setCreativeTab(CreativeTabs.MATERIALS));
			registry.register(ItemRegistrationHelper.newBasicItem("crystal").setCreativeTab(CreativeTabs.MATERIALS));
			registry.register(ItemRegistrationHelper.newBasicItem("crystal_component").setCreativeTab(CreativeTabs.MATERIALS));
			registry.register(ItemRegistrationHelper.newBasicItem("inscribed_crystal_component").setCreativeTab(CreativeTabs.MATERIALS));
		}
		if (PocketknifeConfig.hasAdamantiteTools) {
			ItemRegistrationHelper.registerNewToolSet(registry, "adamantite", ToolMaterialCustom.ADAMANTITE);
		}
		if (PocketknifeConfig.hasCrystallineTools) {
			registry.register(ItemRegistrationHelper.newItem(new ItemBowCustom(640, 3), "crystalline_bow").setCreativeTab(CreativeTabs.COMBAT));
			ItemRegistrationHelper.registerNewToolSet(registry, "crystalline", ToolMaterialCustom.CRYSTALLINE);
		}
	}

	@SubscribeEvent
	public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
		IForgeRegistry<IRecipe> registry = event.getRegistry();
		if (PocketknifeConfig.hasAdamantiteMaterial && PocketknifeConfig.hasCrystallineMaterial) {
			if (PocketknifeConfig.expensiveCrystalRecipe) {
				CompressorRecipes.addRecipe(new ItemStack(ItemHolder.CRYSTAL), "YYY", "YXY", "YYY", 'X', new ItemStack(ItemHolder.CRYSTAL_CORE), 'Y', new ItemStack(Items.DIAMOND));
			} else {
				CompressorRecipes.addRecipe(new ItemStack(ItemHolder.CRYSTAL), " Y ", "YXY", " Y ", 'X', new ItemStack(ItemHolder.CRYSTAL_CORE), 'Y', new ItemStack(Items.DIAMOND));
			}
		}
		if (PocketknifeConfig.hasAdamantiteMaterial) {
			CompressorRecipes.addRecipe(new ItemStack(ItemHolder.ADAMANTITE_INGOT), "XYB", "ZAZ", "BYX", 'X', new ItemStack(AsteroidsItems.basicItem, 1, 6), 'Y', new ItemStack(VenusItems.basicItem, 1, 1), 'Z', new ItemStack(MarsItems.marsItemBasic, 1, 2), 'A', new ItemStack(Items.DIAMOND), 'B', new ItemStack(GCItems.basicItem, 1, 10));
		}
		if (PocketknifeConfig.hasCrystallineMaterial) {
			AE2Api.registerInscriberRecipe(InscriberProcessType.INSCRIBE, new ItemStack(ItemHolder.INSCRIBED_CRYSTAL_COMPONENT), AE2Api.getItemStack(AE2Api.getAE2Materials().engProcessorPrint(), 1), AE2Api.getItemStack(AE2Api.getAE2Materials().siliconPrint(), 1), new ItemStack(ItemHolder.CRYSTAL_COMPONENT));
		}

		if (!PocketknifeConfig.hasAdamantiteMaterial || ! PocketknifeConfig.hasAdamantiteTools) {
			((IForgeRegistryModifiable<IRecipe>)registry).remove(new ResourceLocation("pocketknife:adamantite_axe"));
			((IForgeRegistryModifiable<IRecipe>)registry).remove(new ResourceLocation("pocketknife:adamantite_hoe"));
			((IForgeRegistryModifiable<IRecipe>)registry).remove(new ResourceLocation("pocketknife:adamantite_pickaxe"));
			((IForgeRegistryModifiable<IRecipe>)registry).remove(new ResourceLocation("pocketknife:adamantite_shovel"));
			((IForgeRegistryModifiable<IRecipe>)registry).remove(new ResourceLocation("pocketknife:adamantite_sword"));
		}

		if (!PocketknifeConfig.hasCrystallineMaterial || !PocketknifeConfig.hasCrystallineTools) {
			((IForgeRegistryModifiable<IRecipe>)registry).remove(new ResourceLocation("pocketknife:crystalline_axe"));
			((IForgeRegistryModifiable<IRecipe>)registry).remove(new ResourceLocation("pocketknife:crystalline_hoe"));
			((IForgeRegistryModifiable<IRecipe>)registry).remove(new ResourceLocation("pocketknife:crystalline_pickaxe"));
			((IForgeRegistryModifiable<IRecipe>)registry).remove(new ResourceLocation("pocketknife:crystalline_shovel"));
			((IForgeRegistryModifiable<IRecipe>)registry).remove(new ResourceLocation("pocketknife:crystalline_sword"));
			((IForgeRegistryModifiable<IRecipe>)registry).remove(new ResourceLocation("pocketknife:crystalline_bow"));
		}

		if (!PocketknifeConfig.hasCrystallineMaterial) {
			((IForgeRegistryModifiable<IRecipe>)registry).remove(new ResourceLocation("pocketknife:crystal_core"));
			((IForgeRegistryModifiable<IRecipe>)registry).remove(new ResourceLocation("pocketknife:crystal_component"));
		}
	}
}
