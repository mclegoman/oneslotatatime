package com.mclegoman.oneslotatatime.mixin;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerEntityMixin {
	@Shadow @Final Inventory inventory;
	@Inject(method = "tick", at = @At("HEAD"))
	public void mclmosaat_tick(CallbackInfo ci) {
		this.inventory.selected = 0;
	}
}
