package afyber.pocketknife.items;

import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public final class PocketknifeItems {
	private PocketknifeItems() {}

	private static final ArrayList<Item> allItems = new ArrayList<>();

	public static List<Item> allItems() {
		return allItems;
	}

}
