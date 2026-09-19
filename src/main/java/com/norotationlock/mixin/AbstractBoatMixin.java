package com.norotationlock.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Stops boats (and rafts) from clamping the rider's body rotation to the hull's yaw.
 * Targeting the shared superclass covers Boat, ChestBoat, Raft and ChestRaft alike.
 */
@Mixin(AbstractBoat.class)
public abstract class AbstractBoatMixin {

    @Inject(method = "clampRotation", at = @At("HEAD"), cancellable = true)
    private void norotationlock$removeClampRotation(Entity entity, CallbackInfo ci) {
        if (entity instanceof Player) {
            entity.setYBodyRot(entity.getYHeadRot());
            ci.cancel();
        }
    }
}
