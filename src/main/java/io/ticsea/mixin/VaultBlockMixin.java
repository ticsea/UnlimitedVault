package io.ticsea.mixin;

import io.ticsea.item.ActiveTrialKey;
import io.ticsea.CleanSet;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.VaultBlock;
import net.minecraft.world.level.block.entity.vault.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VaultBlock.class)
public abstract class VaultBlockMixin {
    @Shadow @Final public static Property<VaultState> STATE;

    @Inject(method = "useItemOn",
            at = @At("HEAD"))
    private void useItemOn(ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult, CallbackInfoReturnable<InteractionResult> cir) {
        if (!itemStack.isEmpty() && itemStack.getItem().equals(ActiveTrialKey.ACTIVE_TRIAL_KEY) && blockState.getValue(STATE).equals(VaultState.INACTIVE)) {
            if (level instanceof ServerLevel serverLevel) {
                if (serverLevel.getBlockEntity(blockPos) instanceof VaultBlockEntity vaultBlockEntity) {
                    VaultConfig vaultConfig = vaultBlockEntity.getConfig();
                    VaultServerData vaultServerData = vaultBlockEntity.getServerData();
                    VaultSharedData vaultSharedData = vaultBlockEntity.getSharedData();
                    BlockState blockState2 = blockState.setValue(VaultBlock.STATE, VaultState.ACTIVE);

                    ((CleanSet)vaultServerData).unlimitedvualt_cleanSet();
                    setVaultState(serverLevel, blockPos, blockState, blockState2, vaultConfig, vaultSharedData);
                }
            }
        }
    }

    private static void setVaultState(
            ServerLevel serverLevel, BlockPos blockPos, BlockState blockState, BlockState blockState2, VaultConfig vaultConfig, VaultSharedData vaultSharedData
    ) {
        VaultState vaultState = blockState.getValue(VaultBlock.STATE);
        VaultState vaultState2 = blockState2.getValue(VaultBlock.STATE);
        serverLevel.setBlock(blockPos, blockState2, 3);
        vaultState.onTransition(serverLevel, blockPos, vaultState2, vaultConfig, vaultSharedData, (Boolean)blockState2.getValue(VaultBlock.OMINOUS));
    }
}
