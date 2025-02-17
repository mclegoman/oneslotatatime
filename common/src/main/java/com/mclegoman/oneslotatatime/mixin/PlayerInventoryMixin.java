package com.mclegoman.oneslotatatime.mixin;

import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Inventory.class)
public abstract class PlayerInventoryMixin {
	@Shadow public int selected;
	@Shadow @Final public NonNullList<ItemStack> items;
	@Inject(method = "getSelected", at = @At("HEAD"), cancellable = true)
	public void mclmosaat_getMainHandStack(CallbackInfoReturnable<ItemStack> cir) {
		this.selected = 0;
		cir.setReturnValue(this.items.getFirst());
	}
	@Inject(method = "getSelectionSize", at = @At("HEAD"), cancellable = true)
	private static void mclmosaat_getHotbarSize(CallbackInfoReturnable<Integer> cir) {
		cir.setReturnValue(1);
	}
}
