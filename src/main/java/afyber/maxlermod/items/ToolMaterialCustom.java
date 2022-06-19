package afyber.maxlermod.items;

import net.minecraft.item.Item;
import net.minecraftforge.common.util.EnumHelper;

public final class ToolMaterialCustom {
	private ToolMaterialCustom() {}

	public static final Item.ToolMaterial ADAMANTITE = EnumHelper.addToolMaterial("ADAMANTITE", 4, 2048, 12.0f, 3.0f, 14);
	public static final Item.ToolMaterial CRYSTALLINE = EnumHelper.addToolMaterial("CRYSTALLINE", 5, 4096, 24.0f, 8.0f, 18);
}
