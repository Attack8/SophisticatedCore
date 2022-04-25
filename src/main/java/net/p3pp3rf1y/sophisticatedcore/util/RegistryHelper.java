package net.p3pp3rf1y.sophisticatedcore.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.apache.commons.lang3.Validate;

import java.util.Optional;

public class RegistryHelper {
	private RegistryHelper() {}

	public static ResourceLocation getItemKey(Item item) {
		ResourceLocation itemKey = ForgeRegistries.ITEMS.getKey(item);
		Validate.notNull(itemKey, "itemKey");
		return itemKey;
	}

	public static Optional<ResourceLocation> getRegistryName(ForgeRegistryEntry<?> registryEntry) {
		return Optional.ofNullable(registryEntry.getRegistryName());
	}
}