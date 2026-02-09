package com.norotationlock.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {

    // Prevent vanilla clamping of the passenger's look direction while riding.
    // This allows the player to freely rotate their view while mounted.
    @Inject(method = "onPassengerLookAround", at = @At("HEAD"), cancellable = true)
    private void norotationlock$cancelLookAroundClamp(Entity passenger, CallbackInfo ci) {
        if (passenger instanceof PlayerEntity) {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player != null && passenger == client.player) {
                ci.cancel();
            }
        }
    }
}
