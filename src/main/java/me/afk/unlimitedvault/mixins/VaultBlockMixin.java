package me.afk.unlimitedvault.mixins;


import me.afk.unlimitedvault.mixinInterface.CleanSet;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
        //? if <= 1.21.1 {
/*import net.minecraft.world.ItemInteractionResult;
        *///?}
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.VaultBlock;
import net.minecraft.world.level.block.entity.vault.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VaultBlock.class)
public abstract class VaultBlockMixin {
    @Shadow @Final public static Property<@org.jetbrains.annotations.NotNull VaultState> STATE;

    @Inject(method = "useItemOn",
            at = @At("HEAD"), cancellable = true)
    private void useItemOn(ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult hitResult, CallbackInfoReturnable cir) {
        if (!itemStack.isEmpty() && itemStack.getItem().equals(Items.EMERALD) && blockState.getValue(STATE).equals(VaultState.INACTIVE)) {
            if (level instanceof ServerLevel serverLevel) {
                if (serverLevel.getBlockEntity(blockPos) instanceof VaultBlockEntity vaultBlockEntity) {
                    VaultConfig vaultConfig = vaultBlockEntity.getConfig();
                    VaultServerData vaultServerData = vaultBlockEntity.getServerData();
                    VaultSharedData vaultSharedData = vaultBlockEntity.getSharedData();
                    BlockState blockState2 = blockState.setValue(VaultBlock.STATE, VaultState.ACTIVE);

                    if (vaultServerData == null) return;

                    ((CleanSet)vaultServerData).unlimitedvualt_cleanSet();
                    unlimitedVault$setVaultState(serverLevel, blockPos, blockState, blockState2, vaultConfig, vaultSharedData);

                    itemStack.consume(1, player);

                    //? if > 1.21.1 {
                    cir.setReturnValue(InteractionResult.SUCCESS_SERVER);
                    //?} else {
                    /*cir.setReturnValue(ItemInteractionResult.SUCCESS);
                     *///?}
                }
            }
        }
    }

    @Unique
    private static void unlimitedVault$setVaultState(
            ServerLevel serverLevel, BlockPos blockPos, BlockState blockState, BlockState blockState2, VaultConfig vaultConfig, VaultSharedData vaultSharedData
    ) {
        VaultState vaultState = blockState.getValue(VaultBlock.STATE);
        VaultState vaultState2 = blockState2.getValue(VaultBlock.STATE);
        serverLevel.setBlock(blockPos, blockState2, 3);
        vaultState.onTransition(serverLevel, blockPos, vaultState2, vaultConfig, vaultSharedData, blockState2.getValue(VaultBlock.OMINOUS));
    }
}
