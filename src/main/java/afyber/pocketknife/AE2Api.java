package afyber.pocketknife;

import afyber.pocketknife.error.PocketknifeError;
import appeng.api.AEApi;
import appeng.api.IAppEngApi;
import appeng.api.definitions.IItemDefinition;
import appeng.api.definitions.IMaterials;
import appeng.api.features.IInscriberRecipeBuilder;
import appeng.api.features.InscriberProcessType;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Optional;

public class AE2Api {
	private AE2Api() {}

	private static IAppEngApi apiRef;

	public static void setupAPI() {
		apiRef = AEApi.instance();
	}

	public static IAppEngApi getApi() {
		return apiRef;
	}

	public static IMaterials getAE2Materials() {
		return apiRef.definitions().materials();
	}

	public static ItemStack getItemStack(IItemDefinition definition, int number) {
		Optional<ItemStack> optional = definition.maybeStack(number);
		if (optional.isPresent()) {
			return optional.get();
		}

		String ident = definition.identifier();
		Pocketknife.logger().error("AE2 Api had no ItemStack for IItemDefinition: {}", ident);
		throw new PocketknifeError();
	}

	public static void registerInscriberRecipe(InscriberProcessType type, ItemStack result, @Nullable ItemStack top, @Nullable ItemStack bottom, ItemStack... ingredients) {
		IInscriberRecipeBuilder builder = apiRef.registries().inscriber().builder();
		builder.withProcessType(type);
		builder.withOutput(result);
		builder.withInputs(Arrays.asList(ingredients));
		if (top != null) {
			builder.withTopOptional(top);
		}
		if (bottom != null) {
			builder.withBottomOptional(bottom);
		}
		apiRef.registries().inscriber().addRecipe(builder.build());
	}
}
