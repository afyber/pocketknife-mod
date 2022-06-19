package afyber.maxlermod.items;

import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public final class MaxlerItems {
	private MaxlerItems() {}

	private static final ArrayList<Item> allItems = new ArrayList<>();

	public static List<Item> allItems() {
		return allItems;
	}

}
