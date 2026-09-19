package com.norotationlock.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {

    // Prevent vanilla clamping of the passenger's look direction while riding.
    // This allows the player to freely rotate their view while mounted.
    @Inject(method = "onPassengerTurned", at = @At("HEAD"), cancellable = true)
    private void norotationlock$cancelLookAroundClamp(Entity passenger, CallbackInfo ci) {
        if (passenger instanceof Player) {
            Minecraft client = Minecraft.getInstance();
            if (client.player != null && passenger == client.player) {
                ci.cancel();
            }
        }
    }
}
