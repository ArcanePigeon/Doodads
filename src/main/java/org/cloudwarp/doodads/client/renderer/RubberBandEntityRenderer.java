package org.cloudwarp.doodads.client.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.TridentEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3f;
import org.cloudwarp.doodads.Doodads;
import org.cloudwarp.doodads.entities.RubberBandEntity;

import java.util.Random;

import static org.cloudwarp.doodads.utils.DoodadsItemTypes.RUBBER_BAND;

@Environment(value= EnvType.CLIENT)
public class RubberBandEntityRenderer extends EntityRenderer<RubberBandEntity> {
	public static final Identifier TEXTURE = Doodads.id("textures/item/rubber_band.png");
	private final ItemRenderer itemRenderer;
	private final Random random = new Random();
	public RubberBandEntityRenderer(EntityRendererFactory.Context context) {
		super(context);
		this.itemRenderer = context.getItemRenderer();
	}

	@Override
	public void render(RubberBandEntity rubberBandEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
		matrixStack.push();
		matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(MathHelper.lerp(g, rubberBandEntity.prevYaw, rubberBandEntity.getYaw()) - 90.0f));
		matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(MathHelper.lerp(g, rubberBandEntity.prevPitch, rubberBandEntity.getPitch()) + 90.0f));
		this.itemRenderer.renderItem(rubberBandEntity.asItemStack(), ModelTransformation.Mode.GROUND, i, OverlayTexture.DEFAULT_UV, matrixStack, vertexConsumerProvider, rubberBandEntity.getId());

		matrixStack.pop();
		super.render(rubberBandEntity, f, g, matrixStack, vertexConsumerProvider, i);
	}

	@Override
	public Identifier getTexture(RubberBandEntity arrowEntity) {
		//return TEXTURE;
		return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
	}
}
