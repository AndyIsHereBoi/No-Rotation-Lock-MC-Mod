package com.norotationlock.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BoatEntity.class)
public abstract class BoatEntityMixin {

    @Inject(method = "copyEntityData", at = @At("HEAD"), cancellable = true)
    private void norotationlock$removeCopyEntityDataClamp(Entity entity, CallbackInfo ci) {
        if (entity instanceof PlayerEntity) {
            entity.setBodyYaw(entity.getHeadYaw());
            ci.cancel();
        }
    }
}
