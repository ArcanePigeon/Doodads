package org.cloudwarp.doodads.mixin;

import dev.emi.trinkets.api.TrinketsApi;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.BlockTags;
import net.minecraft.world.World;
import org.cloudwarp.doodads.item.SlingShotItem;
import org.cloudwarp.doodads.interfaces.PlayerEntityInterface;
import org.cloudwarp.doodads.registry.DItems;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;

import static org.cloudwarp.doodads.utils.DoodadsItemTypes.*;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements PlayerEntityInterface {

	@Shadow
	@Final
	private PlayerInventory inventory;
	@Shadow
	@Final
	private  PlayerAbilities abilities;

	protected PlayerEntityMixin (EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	public ItemStack getPebbleType(ItemStack stack) {
		if (!(stack.getItem() instanceof SlingShotItem)) {
			return ItemStack.EMPTY;
		}
		Predicate<ItemStack> predicate = ((SlingShotItem)stack.getItem()).getHeldProjectiles();
		ItemStack itemStack = SlingShotItem.getHeldProjectile(this, predicate);
		if (!itemStack.isEmpty()) {
			return itemStack;
		}
		predicate = ((SlingShotItem)stack.getItem()).getProjectiles();
		for (int i = 0; i < this.inventory.size(); ++i) {
			ItemStack itemStack2 = this.inventory.getStack(i);
			if (!predicate.test(itemStack2)) continue;
			return itemStack2;
		}
		return this.abilities.creativeMode ? new ItemStack(DItems.get("slingshot")) : ItemStack.EMPTY;
	}

	@Inject(method = "getBlockBreakingSpeed", at = @At(value = "RETURN", target = "Lnet/minecraft/entity/player/PlayerEntity;getBlockBreakingSpeed(Lnet/minecraft/block/BlockState;)F"), cancellable = true)
	public void getBlockBreakingSpeed(BlockState block, CallbackInfoReturnable<Float> cir) {
		if(TrinketsApi.getTrinketComponent((LivingEntity)((Object)this)).get().isEquipped(BEAVER_TEETH.item()) &&
				(block.isIn(BlockTags.LOGS) ||
						block.isIn(BlockTags.PLANKS) ||
						block.isIn(BlockTags.WOODEN_DOORS) ||
						block.isIn(BlockTags.WOODEN_BUTTONS) ||
						block.isIn(BlockTags.WOODEN_FENCES) ||
						block.isIn(BlockTags.WOODEN_SLABS) ||
						block.isIn(BlockTags.WOODEN_STAIRS) ||
						block.isIn(BlockTags.WOODEN_TRAPDOORS) ||
						block.isIn(BlockTags.WOODEN_PRESSURE_PLATES))){
			cir.setReturnValue(cir.getReturnValueF()*100f);
		}
	}
}
