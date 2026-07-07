// Copyright 2020-2026 Mirsario & Contributors.
// Released under the GNU General Public License 3.0.
// See LICENSE.md for details.

//? if NEOFORGE {
/*package me.afk.unlimitedvault.entrypoints;
import me.afk.unlimitedvault.UnlimitedVault;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.*;
import net.neoforged.neoforge.common.NeoForge;

@Mod(UnlimitedVault.MOD_ID)
@SuppressWarnings("unused")
public class NeoForgeInitializer {
	public NeoForgeInitializer() {
		ModLoadingContext modLoadingContext = ModLoadingContext.get();
		modLoadingContext.registerExtensionPoint(IConfigScreenFactory.class, () -> (mc, p) -> null);
	}
}
*///?}
