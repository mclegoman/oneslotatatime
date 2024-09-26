package com.mclegoman.oneslotatatime.mixin;

import net.minecraft.entity.player.PlayerInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerInventory.class)
public abstract class PlayerInventoryMixin {
	@Shadow public int selectedSlot;
	@Inject(method = "scrollInHotbar", at = @At("HEAD"), cancellable = true)
	private void mclmosaat_scrollInHotbar(double scrollAmount, CallbackInfo ci) {
		ci.cancel();
		this.selectedSlot = 0;
	}
}
