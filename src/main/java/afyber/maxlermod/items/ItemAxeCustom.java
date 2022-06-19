package afyber.maxlermod.items;

import net.minecraft.item.ItemAxe;

import java.util.EnumMap;

public class ItemAxeCustom extends ItemAxe {

	private static final EnumMap<ToolMaterial, Float> ATTACK_DAMAGES = new EnumMap<>(ToolMaterial.class);
	private static final EnumMap<ToolMaterial, Float> ATTACK_SPEEDS = new EnumMap<>(ToolMaterial.class);
	static {
		ATTACK_DAMAGES.put(ToolMaterialCustom.ADAMANTITE, 11.0f);
		ATTACK_DAMAGES.put(ToolMaterialCustom.CRYSTALLINE, 17.0f);
		ATTACK_SPEEDS.put(ToolMaterialCustom.ADAMANTITE, -2.8f);
		ATTACK_SPEEDS.put(ToolMaterialCustom.CRYSTALLINE, -2.2f);
	}

	public ItemAxeCustom(ToolMaterial material) {
		super(material, ATTACK_DAMAGES.get(material), ATTACK_SPEEDS.get(material));
	}
}
