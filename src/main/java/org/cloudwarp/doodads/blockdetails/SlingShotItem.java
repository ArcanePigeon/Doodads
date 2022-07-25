package org.cloudwarp.doodads.blockdetails;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.tag.ItemTags;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.cloudwarp.doodads.entities.SlingShotProjectileEntity;
import org.cloudwarp.doodads.interfaces.PlayerEntityInterface;
import org.cloudwarp.doodads.registry.DItems;
import org.cloudwarp.doodads.utils.DoodadsItemTypes;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

public class SlingShotItem extends BowItem {
	public DoodadsItemTypes doodadsItemType;
	public static final Predicate<ItemStack> SLINGSHOT_PROJECTILES = stack -> stack.isOf(DItems.get("pebble"));

	public SlingShotItem (Settings settings, DoodadsItemTypes doodadsItemType) {
		super(settings);
		this.doodadsItemType = doodadsItemType;
	}

	public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
		if (user instanceof PlayerEntity playerEntity) {
			boolean bl = playerEntity.getAbilities().creativeMode || EnchantmentHelper.getLevel(Enchantments.INFINITY, stack) > 0;
			ItemStack itemStack = ((PlayerEntityInterface)playerEntity).getPebbleType(stack);
			if (!itemStack.isEmpty() || bl) {
				if (itemStack.isEmpty()) {
					itemStack = new ItemStack(DItems.get("pebble"));
				}

				int i = this.getMaxUseTime(stack) - remainingUseTicks;
				float f = getPullProgress(i);
				if (!((double)f < 0.1)) {
					boolean bl2 = bl && itemStack.isOf(DItems.get("pebble"));
					if (!world.isClient) {
						PebbleItem PebbleItem = (PebbleItem)(itemStack.getItem() instanceof PebbleItem ? itemStack.getItem() : DItems.get("pebble"));
						SlingShotProjectileEntity slingShotProjectileEntity = PebbleItem.createPebble(world, itemStack, playerEntity);
						slingShotProjectileEntity.setVelocity(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0F, f * 3.0F, 1.0F);
						if (f == 1.0F) {
							slingShotProjectileEntity.setCritical(true);
						}

						int j = EnchantmentHelper.getLevel(Enchantments.POWER, stack);
						if (j > 0) {
							slingShotProjectileEntity.setDamage(slingShotProjectileEntity.getDamage() + (double)j * 0.5 + 0.5);
						}

						int k = EnchantmentHelper.getLevel(Enchantments.PUNCH, stack);
						if (k > 0) {
							slingShotProjectileEntity.setPunch(k);
						}

						if (EnchantmentHelper.getLevel(Enchantments.FLAME, stack) > 0) {
							slingShotProjectileEntity.setOnFireFor(100);
						}

						stack.damage(1, playerEntity, (p) -> {
							p.sendToolBreakStatus(playerEntity.getActiveHand());
						});

						world.spawnEntity(slingShotProjectileEntity);
					}

					world.playSound((PlayerEntity)null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (world.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F);
					if (!bl2 && !playerEntity.getAbilities().creativeMode) {
						itemStack.decrement(1);
						if (itemStack.isEmpty()) {
							playerEntity.getInventory().removeOne(itemStack);
						}
					}

					playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
				}
			}
		}
	}
	public static float getPullProgress(int useTicks) {
		float f = (float)useTicks / 20.0f;
		if ((f = (f * f + f * 2.0f) / 3.0f) > 1.0f) {
			f = 1.0f;
		}
		return f;
	}
	@Override
	public int getMaxUseTime(ItemStack stack) {
		return 72000;
	}

	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.BOW;
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		boolean bl;
		ItemStack itemStack = user.getStackInHand(hand);
		boolean bl2 = bl = !user.getArrowType(itemStack).isEmpty();
		if (user.getAbilities().creativeMode || bl) {
			user.setCurrentHand(hand);
			return TypedActionResult.consume(itemStack);
		}
		return TypedActionResult.fail(itemStack);
	}

	@Override
	public Predicate<ItemStack> getProjectiles() {
		return SLINGSHOT_PROJECTILES;
	}

	@Override
	public int getRange() {
		return 15;
	}

	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		if(Screen.hasShiftDown()){
			tooltip.add(Text.translatable("item.doodads."+ doodadsItemType.id +".tooltip.shift"));
		}else{
			tooltip.add(Text.translatable("item.doodads.generic_tooltip"));
		}
	}

}

