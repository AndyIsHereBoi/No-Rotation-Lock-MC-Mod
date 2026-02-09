package com.norotationlock;

import com.norotationlock.compat.SmoothCoastersCompat;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;

public class NoRotationLockMod implements ClientModInitializer {

    private boolean norotationlock$wasRiding = false;

    @Override
    public void onInitializeClient() {
        // Monitor the player's mount state and toggle SmoothCoasters rotation limits
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            MinecraftClient mc = client;
            if (mc.player == null) return;
            boolean isRiding = mc.player.hasVehicle();
            if (isRiding) {
                // Ensure limits are disabled while riding (call every tick to defeat updates)
                SmoothCoastersCompat.setUnlimitedRotation(true);
            } else if (norotationlock$wasRiding) {
                // Restore defaults once when dismounting
                norotationlock$wasRiding = false;
                SmoothCoastersCompat.setUnlimitedRotation(false);
            }
            norotationlock$wasRiding = isRiding;
        });
    }
}
