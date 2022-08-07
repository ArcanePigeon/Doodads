package org.cloudwarp.doodads.registry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.jetbrains.annotations.Nullable;

public class DDamageSource extends EntityDamageSource {
	public DDamageSource (String name, Entity source) {
		super(name, source);
	}

	public static class ScissorsDamageSource extends DDamageSource{
		@Nullable
		private final ItemStack scissors;
		public ScissorsDamageSource (String name, Entity source, @Nullable ItemStack scissors) {
			super(name, source);
			this.scissors = scissors;
		}
		@Override
		public Text getDeathMessage(LivingEntity entity) {
			String string = "death.attack." + this.name;
			if (!scissors.isEmpty() && scissors.hasCustomName()) {
				return new TranslatableText(string + ".item", entity.getDisplayName(), scissors.toHoverableText());
			}
			return new TranslatableText(string, entity.getDisplayName());
		}
	}

	public static class DProjectileDamageSource extends DDamageSource{
		@Nullable
		private final Entity attacker;

		public DProjectileDamageSource (String name, Entity projectile, @Nullable Entity attacker) {
			super(name, projectile);
			this.attacker = attacker;
		}

		@Nullable
		public Entity getSource () {
			return this.source;
		}

		@Nullable
		public Entity getAttacker () {
			return this.attacker;
		}

		public Text getDeathMessage (LivingEntity entity) {
			Text text = this.attacker == null ? this.source.getDisplayName() : this.attacker.getDisplayName();
			ItemStack itemStack = this.attacker instanceof LivingEntity ? ((LivingEntity) this.attacker).getMainHandStack() : ItemStack.EMPTY;
			String string = "death.attack." + this.name;
			String string2 = string + ".item";
			return ! itemStack.isEmpty() && itemStack.hasCustomName() ? new TranslatableText(string2, new Object[] {entity.getDisplayName(), text, itemStack.toHoverableText()}) : new TranslatableText(string, new Object[] {entity.getDisplayName(), text});
		}
	}
}
