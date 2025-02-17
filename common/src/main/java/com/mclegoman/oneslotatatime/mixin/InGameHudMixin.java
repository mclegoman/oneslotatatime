package com.mclegoman.oneslotatatime.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.AttackIndicatorStatus;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class InGameHudMixin {
	@Shadow @Nullable protected abstract Player getCameraPlayer();
	@Shadow @Final private static ResourceLocation HOTBAR_OFFHAND_LEFT_SPRITE;
	@Shadow @Final private static ResourceLocation HOTBAR_OFFHAND_RIGHT_SPRITE;
	@Shadow @Final private Minecraft minecraft;
	@Shadow @Final private static ResourceLocation HOTBAR_ATTACK_INDICATOR_BACKGROUND_SPRITE;
	@Shadow @Final private static ResourceLocation HOTBAR_ATTACK_INDICATOR_PROGRESS_SPRITE;
	@Shadow protected abstract void renderSlot(GuiGraphics guiGraphics, int i, int j, DeltaTracker deltaTracker, Player player, ItemStack itemStack, int k);
	@Inject(method = "renderItemHotbar", at = @At("HEAD"), cancellable = true)
	private void mclmosaat_overrideHotbar(GuiGraphics context, DeltaTracker tickCounter, CallbackInfo ci) {
		ci.cancel();
		Player playerEntity = this.getCameraPlayer();
		if (playerEntity != null) {
			int halfWidth = context.guiWidth() / 2;
			HumanoidArm offHand = playerEntity.getMainArm().getOpposite();
			ItemStack offHandStack = playerEntity.getOffhandItem();
			HumanoidArm mainHand = playerEntity.getMainArm();
			ItemStack mainHandStack = playerEntity.getMainHandItem();
			RenderSystem.enableBlend();
			context.pose().pushPose();
			context.pose().translate(0.0F, 0.0F, -90.0F);
			if (!offHandStack.isEmpty()) {
				if (offHand == HumanoidArm.LEFT) context.blitSprite(HOTBAR_OFFHAND_LEFT_SPRITE, halfWidth - 91 - 29, context.guiHeight() - 23, 29, 24);
				else context.blitSprite(HOTBAR_OFFHAND_RIGHT_SPRITE, halfWidth + 91, context.guiHeight() - 23, 29, 24);
			}
			if (!mainHandStack.isEmpty()) {
				if (mainHand == HumanoidArm.LEFT) {
					context.blitSprite(HOTBAR_OFFHAND_LEFT_SPRITE, halfWidth - 91 - 29, context.guiHeight() - 23, 29, 24);
				} else {
					context.blitSprite(HOTBAR_OFFHAND_RIGHT_SPRITE, halfWidth + 91, context.guiHeight() - 23, 29, 24);
				}
			}
			context.pose().popPose();
			RenderSystem.disableBlend();
			int m;
			int l = 1;
			int n = halfWidth - 90 + 2;
			if (!offHandStack.isEmpty()) {
				m = context.guiHeight() - 16 - 3;
				if (offHand == HumanoidArm.LEFT) {
					this.renderSlot(context, halfWidth - 91 - 26, m, tickCounter, playerEntity, offHandStack, l++);
				} else {
					this.renderSlot(context, halfWidth + 91 + 10, m, tickCounter, playerEntity, offHandStack, l++);
				}
			}
			if (!mainHandStack.isEmpty()) {
				m = context.guiHeight() - 16 - 3;
				if (mainHand == HumanoidArm.LEFT) {
					this.renderSlot(context, halfWidth - 91 - 26, m, tickCounter, playerEntity, mainHandStack, l++);
				} else {
					this.renderSlot(context, halfWidth + 91 + 10, m, tickCounter, playerEntity, mainHandStack, l++);
				}
			}
			if (this.minecraft.options.attackIndicator().get() == AttackIndicatorStatus.HOTBAR) {
				RenderSystem.enableBlend();
				assert this.minecraft.player != null;
				float f = this.minecraft.player.getAttackStrengthScale(0.0F);
				if (f < 1.0F) {
					int p = (int)(f * 19.0F);
					context.blitSprite(HOTBAR_ATTACK_INDICATOR_BACKGROUND_SPRITE, halfWidth - 9, n, 18, 18);
					context.blitSprite(HOTBAR_ATTACK_INDICATOR_PROGRESS_SPRITE, 18, 18, 0, 18 - p, halfWidth - 9, n + 18 - p, 18, p);
				}
				RenderSystem.disableBlend();
			}
		}
	}
}
