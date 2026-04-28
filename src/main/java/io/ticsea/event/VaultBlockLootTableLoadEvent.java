package io.ticsea.event;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.*;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.Collection;

public class VaultBlockLootTableLoadEvent {
    public static void init() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if (Blocks.VAULT.getLootTable().isPresent()
                    && Blocks.VAULT.getLootTable().get() == key
                    && source.isBuiltin()) {
                LootPool.Builder pool = LootPool.lootPool()
                        .with(LootItem.lootTableItem(Items.NETHERITE_BLOCK)
                                .setWeight(90)
                                .build());
                        tableBuilder.withPool(pool);
            }
        });
    }
}
