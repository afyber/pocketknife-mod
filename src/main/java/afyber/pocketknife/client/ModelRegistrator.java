package afyber.pocketknife.client;

import afyber.pocketknife.Pocketknife;
import afyber.pocketknife.items.PocketknifeItems;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = Pocketknife.MOD_ID)
public final class ModelRegistrator {
	private ModelRegistrator() {}

	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		for (Item item: PocketknifeItems.allItems()) {
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
		}
	}

}
