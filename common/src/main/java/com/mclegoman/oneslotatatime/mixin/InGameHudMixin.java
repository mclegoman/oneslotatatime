package com.mclegoman.oneslotatatime.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.option.AttackIndicator;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
	@Shadow @Nullable
	protected abstract PlayerEntity getCameraPlayer();
	@Shadow @Final private static Identifier HOTBAR_OFFHAND_LEFT_TEXTURE;
	@Shadow @Final private static Identifier HOTBAR_OFFHAND_RIGHT_TEXTURE;
	@Shadow protected abstract void renderHotbarItem(DrawContext context, int x, int y, RenderTickCounter tickCounter, PlayerEntity player, ItemStack stack, int seed);
	@Shadow @Final private MinecraftClient client;
	@Shadow @Final private static Identifier HOTBAR_ATTACK_INDICATOR_BACKGROUND_TEXTURE;
	@Shadow @Final private static Identifier HOTBAR_ATTACK_INDICATOR_PROGRESS_TEXTURE;
	@Inject(method = "renderHotbarVanilla", at = @At("HEAD"), cancellable = true)
	private void mclmosaat_overrideHotbar(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
		ci.cancel();
		PlayerEntity playerEntity = this.getCameraPlayer();
		if (playerEntity != null) {
			int halfWidth = context.getScaledWindowWidth() / 2;
			Arm offHand = playerEntity.getMainArm().getOpposite();
			ItemStack offHandStack = playerEntity.getOffHandStack();
			Arm mainHand = playerEntity.getMainArm();
			ItemStack mainHandStack = playerEntity.getMainHandStack();
			RenderSystem.enableBlend();
			context.getMatrices().push();
			context.getMatrices().translate(0.0F, 0.0F, -90.0F);
			if (!offHandStack.isEmpty()) {
				if (offHand == Arm.LEFT) context.drawGuiTexture(HOTBAR_OFFHAND_LEFT_TEXTURE, halfWidth - 91 - 29, context.getScaledWindowHeight() - 23, 29, 24);
				else context.drawGuiTexture(HOTBAR_OFFHAND_RIGHT_TEXTURE, halfWidth + 91, context.getScaledWindowHeight() - 23, 29, 24);
			}
			if (!mainHandStack.isEmpty()) {
				if (mainHand == Arm.LEFT) {
					context.drawGuiTexture(HOTBAR_OFFHAND_LEFT_TEXTURE, halfWidth - 91 - 29, context.getScaledWindowHeight() - 23, 29, 24);
				} else {
					context.drawGuiTexture(HOTBAR_OFFHAND_RIGHT_TEXTURE, halfWidth + 91, context.getScaledWindowHeight() - 23, 29, 24);
				}
			}
			context.getMatrices().pop();
			RenderSystem.disableBlend();
			int m;
			int l = 1;
			int n = halfWidth - 90 + 2;
			if (!offHandStack.isEmpty()) {
				m = context.getScaledWindowHeight() - 16 - 3;
				if (offHand == Arm.LEFT) {
					this.renderHotbarItem(context, halfWidth - 91 - 26, m, tickCounter, playerEntity, offHandStack, l++);
				} else {
					this.renderHotbarItem(context, halfWidth + 91 + 10, m, tickCounter, playerEntity, offHandStack, l++);
				}
			}
			if (!mainHandStack.isEmpty()) {
				m = context.getScaledWindowHeight() - 16 - 3;
				if (mainHand == Arm.LEFT) {
					this.renderHotbarItem(context, halfWidth - 91 - 26, m, tickCounter, playerEntity, mainHandStack, l++);
				} else {
					this.renderHotbarItem(context, halfWidth + 91 + 10, m, tickCounter, playerEntity, mainHandStack, l++);
				}
			}
			if (this.client.options.getAttackIndicator().getValue() == AttackIndicator.HOTBAR) {
				RenderSystem.enableBlend();
				assert this.client.player != null;
				float f = this.client.player.getAttackCooldownProgress(0.0F);
				if (f < 1.0F) {
					int p = (int)(f * 19.0F);
					context.drawGuiTexture(HOTBAR_ATTACK_INDICATOR_BACKGROUND_TEXTURE, halfWidth - 9, n, 18, 18);
					context.drawGuiTexture(HOTBAR_ATTACK_INDICATOR_PROGRESS_TEXTURE, 18, 18, 0, 18 - p, halfWidth - 9, n + 18 - p, 18, p);
				}
				RenderSystem.disableBlend();
			}
		}
	}
}
