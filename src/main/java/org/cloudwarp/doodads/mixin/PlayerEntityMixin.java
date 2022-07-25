package org.cloudwarp.doodads.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.world.World;
import org.cloudwarp.doodads.blockdetails.SlingShotItem;
import org.cloudwarp.doodads.interfaces.PlayerEntityInterface;
import org.cloudwarp.doodads.registry.DItems;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.function.Predicate;

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
}
