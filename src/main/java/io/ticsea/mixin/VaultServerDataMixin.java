package io.ticsea.mixin;

import io.ticsea.CleanSet;
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
public abstract class VaultServerDataMixin implements CleanSet {
	@Shadow @Final private Set<UUID> rewardedPlayers;

	@Inject(at = @At("TAIL"), method = "addToRewardedPlayers")
	private void cancleThisAction(CallbackInfo info) {
		rewardedPlayers.clear();
	}

	@Override
	public void unlimitedvualt_cleanSet() {
		rewardedPlayers.clear();
	}
}