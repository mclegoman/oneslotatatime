package com.mclegoman.oneslotatatime.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
	@Shadow @Final PlayerInventory inventory;
	@Inject(method = "tick", at = @At("HEAD"))
	public void mclmosaat_tick(CallbackInfo ci) {
		this.inventory.selectedSlot = 0;
	}
}
