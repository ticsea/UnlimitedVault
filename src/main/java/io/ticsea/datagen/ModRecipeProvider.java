package io.ticsea.datagen;

import io.ticsea.item.ActiveTrialKey;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registryLookup, RecipeOutput exporter) {
        return new RecipeProvider(registryLookup, exporter) {
            @Override
            public void buildRecipes() {
                HolderLookup.RegistryLookup<Item> itemLookup = registries.lookupOrThrow(Registries.ITEM);

                shapeless(RecipeCategory.MISC, ActiveTrialKey.ACTIVE_TRIAL_KEY) // You can also specify an int to produce more than one
                        // You can also specify an int to require more than one, or a tag to accept multiple things
                        .requires(Items.TRIAL_KEY)
                        .requires(Items.EMERALD)
                        // Create an advancement that gives you the recipe
                        .unlockedBy(getHasName(Items.TRIAL_KEY), has(Items.EMERALD))
                        .save(output);
            }
        };
    }



    @Override
    public String getName() {
        return "";
    }
}
