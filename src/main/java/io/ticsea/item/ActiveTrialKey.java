package io.ticsea.item;

import io.ticsea.UnlimitedVault;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.function.Function;

public class ActiveTrialKey {
    public static final Item ACTIVE_TRIAL_KEY = register("active_trial_key", Item::new, new Item.Properties());

    public static void init() {}

    public static Item register(String name, Function<Item.Properties, Item> itemFactory, Item.Properties settings) {
        // Create the item key.
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(UnlimitedVault.MOD_ID, name));

        // Create the item instance.
        Item item = itemFactory.apply(settings.setId(itemKey));

        // Register the item.
        Registry.register(BuiltInRegistries.ITEM, itemKey, item);

        return item;
    }

    public static void addToCreativeTab() {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS).register(fabricItemGroupEntries ->
                fabricItemGroupEntries.addAfter(Items.OMINOUS_TRIAL_KEY.getDefaultInstance(), ACTIVE_TRIAL_KEY)
                );
    }
}
