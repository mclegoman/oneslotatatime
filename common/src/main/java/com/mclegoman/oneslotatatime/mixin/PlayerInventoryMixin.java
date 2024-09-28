package com.mclegoman.oneslotatatime.mixin;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerInventory.class)
public abstract class PlayerInventoryMixin {
	@Shadow @Final public DefaultedList<ItemStack> main;
	@Shadow public int selectedSlot;
	@Inject(method = "getMainHandStack", at = @At("HEAD"), cancellable = true)
	public void mclmosaat_getMainHandStack(CallbackInfoReturnable<ItemStack> cir) {
		this.selectedSlot = 0;
		cir.setReturnValue(this.main.getFirst());
	}
	@Inject(method = "getHotbarSize", at = @At("HEAD"), cancellable = true)
	private static void mclmosaat_getHotbarSize(CallbackInfoReturnable<Integer> cir) {
		cir.setReturnValue(1);
	}
}
