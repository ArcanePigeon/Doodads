package org.cloudwarp.doodads.item;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.tag.BlockTags;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.cloudwarp.doodads.utils.DoodadsItemTypes;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SporeSwordItem extends SwordItem{
	public DoodadsItemTypes doodadsItemType;
	public StatusEffectInstance poison = new StatusEffectInstance(StatusEffects.POISON, 200, 0, true, false);
	private final EntityAttributeModifier damageModifier =  new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", 0.5f, EntityAttributeModifier.Operation.ADDITION);

	public int currentDamage;
	public SporeSwordItem (Settings settings, DoodadsItemTypes doodadsItemType, ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
		super(toolMaterial, attackDamage,attackSpeed,settings);
		this.doodadsItemType = doodadsItemType;
		this.currentDamage = toolMaterial.getDurability();
	}


	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(Text.translatable("item.doodads."+ doodadsItemType.name +".tooltip"));
	}
	@Override
	public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
		return !miner.isCreative();
	}

	@Override
	public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
		if (state.isOf(Blocks.COBWEB)) {
			return 15.0f;
		}
		Material material = state.getMaterial();
		if (material == Material.PLANT || material == Material.REPLACEABLE_PLANT || state.isIn(BlockTags.LEAVES) || material == Material.GOURD) {
			return 1.5f;
		}
		return 1.0f;
	}
	@Override
	public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		//if(isUsable(stack)) {
			//stack.damage(1, attacker, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
			if(target.canHaveStatusEffect(poison)){
				target.addStatusEffect(poison);
			}
		//}
		return true;
	}

	@Override
	public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
		//if (state.getHardness(world, pos) != 0.0f && isUsable(stack)) {
		//	stack.damage(1, miner, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
		//}
		return true;
	}

	@Override
	public boolean isSuitableFor(BlockState state) {
		return state.isOf(Blocks.COBWEB);
	}

	//@Override
	//public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
	//	Multimap<EntityAttribute, EntityAttributeModifier> modifiers = super.getAttributeModifiers(slot);
	//	System.out.println(this.currentDamage);
	//	if(modifiers != null && !modifiers.isEmpty()){
	//		System.out.println("HERE");
	//		modifiers.replaceValues(EntityAttributes.GENERIC_ATTACK_DAMAGE, ImmutableList.of(damageModifier));
	//	}
	//	return modifiers;
	//}

	//public static boolean isUsable(ItemStack stack) {
	//	return stack.getDamage() < stack.getMaxDamage() - 1;
	//}
}

