package com.example.docs;

import com.ibm.icu.impl.Assert;
import net.fabricmc.fabric.api.client.gametest.v1.FabricClientGameTest;
import net.fabricmc.fabric.api.client.gametest.v1.TestInput;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestServerContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestSingleplayerContext;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.VaultBlock;
import net.minecraft.world.level.block.entity.vault.VaultState;
import net.minecraft.world.level.block.state.BlockState;

public class ExampleModClientGameTest implements FabricClientGameTest {

    @Override
    public void runTest(ClientGameTestContext context) {
        try (TestSingleplayerContext singleplayer = context.worldBuilder().create()) {

            // ============ TEST 1: Normal Vault ============
            testNormalVault(context, singleplayer);

            // ============ TEST 2: Ominous Vault ============
            testOminousVault(context, singleplayer);
        }
    }

    private void testNormalVault(ClientGameTestContext context, TestSingleplayerContext singleplayer) {
        // Setup
        TestServerContext server1 = singleplayer.getServer();
        server1.runCommand("gamemode creative @a");
        server1.runCommand("setblock 0 250 0 minecraft:vault");
        server1.runCommand("tp @a 0 251 0");

        // Give and equip trial key
        server1.runCommand("give @a minecraft:trial_key");
        context.waitTicks(20);

        // Look at and interact with vault
        BlockPos vaultPos = new BlockPos(0, 250, 0);
        TestInput input = context.getInput();
        input.lookAt(vaultPos);
        context.waitTicks(5);

        // Right-click the vault
        input.pressMouse(1);
        context.waitTicks(100); // Wait for vault to process

        // Verify vault became active
        server1.runOnServer(server -> {
            BlockState blockState = server.overworld().getBlockState(vaultPos);
            VaultState state = blockState.getValue(VaultBlock.STATE);

            System.out.println("Normal vault state: " + state);
            Assert.assrt(state == VaultState.ACTIVE);
        });

        context.waitTicks(10);
    }

    private void testOminousVault(ClientGameTestContext context, TestSingleplayerContext singleplayer) {
        // Setup ominous vault
        TestServerContext server1 = singleplayer.getServer();
        server1.runCommand("clear @a");
        server1.runCommand("setblock 10 250 0 minecraft:vault[ominous=true]");
        server1.runCommand("tp @a 10 251 0");

        // Give ominous trial key and equip it
        server1.runCommand("give @a minecraft:emerald");
        context.waitTicks(20);

        BlockPos vaultPos = new BlockPos(10, 250, 0);

        // Look at vault
        TestInput input = context.getInput();
        input.lookAt(vaultPos);
        context.waitTicks(5);

        // Interact with ominous vault
        input.pressMouse(1);
        context.waitTicks(40);

        // Verify vault state
        server1.runOnServer(server -> {
            BlockState blockState = server.overworld().getBlockState(vaultPos);
            VaultState state = blockState.getValue(VaultBlock.STATE);

            System.out.println("Ominous vault state: " + state);
            Assert.assrt(state == VaultState.ACTIVE);

            // Verify it's still ominous
            boolean isOminous = blockState.getValue(VaultBlock.OMINOUS);
            Assert.assrt(isOminous);
        });

        context.waitTicks(10);
    }
}