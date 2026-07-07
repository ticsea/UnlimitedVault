package me.afk.unlimitedvault.mixins;

import me.afk.unlimitedvault.mixinInterface.CleanSet;
import net.minecraft.world.level.block.entity.vault.VaultServerData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;
import java.util.UUID;

@Mixin(VaultServerData.class)
public class VaultServerDateMixin implements CleanSet {
    @Shadow
    @Final
    private Set<UUID> rewardedPlayers;

    @Inject(method = "addToRewardedPlayers", at = @At("TAIL"))
    private void clean(CallbackInfo info) {
        //todo testing
        rewardedPlayers.clear();
    }

    @Override
    public void unlimitedvualt_cleanSet() {
        rewardedPlayers.clear();
    }
}
