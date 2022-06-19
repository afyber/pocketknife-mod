package afyber.maxlermod.client;

import afyber.maxlermod.MaxlerMod;
import afyber.maxlermod.items.MaxlerItems;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = MaxlerMod.MOD_ID)
public final class ModelRegistrator {
	private ModelRegistrator() {}

	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		for (Item item: MaxlerItems.allItems()) {
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
		}
	}

}
