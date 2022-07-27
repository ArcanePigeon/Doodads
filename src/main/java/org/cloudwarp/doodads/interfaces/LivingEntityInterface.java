package org.cloudwarp.doodads.interfaces;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import org.cloudwarp.doodads.mixin.LivingEntityMixin;

public interface LivingEntityInterface {
	boolean hasPlum();
	void setPlum(boolean hasPlum);
}
