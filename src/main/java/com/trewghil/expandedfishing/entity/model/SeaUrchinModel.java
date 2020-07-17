package com.trewghil.expandedfishing.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;


@Environment(EnvType.CLIENT)
public class SeaUrchinModel extends Model {

	private final ModelPart body = new ModelPart(16, 16, 0, 0);

	public SeaUrchinModel() {
		super(RenderLayer::getEntitySolid);

		this.body.setPivot(0.0F, 0.0F, 0.0F);
		this.body.setTextureOffset(0, 0).addCuboid(-1F, 3F, -1F, 2.0F, 2.0F, 2.0F, 0.0F);
		this.body.setTextureOffset(0, 0).addCuboid(0.0F, 2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 0.0F);
		this.body.setTextureOffset(0, 2).addCuboid(-2.0F, 2.0F, 0.0F, 4.0F, 4.0F, 0.0F, 0.0F);
		this.body.setTextureOffset(0, 0).addCuboid(-2.0F, 4.0F, -2.0F, 4.0F, 0.0F, 4.0F, 0.0F);
	}


	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		this.body.render(matrices, vertices, light, overlay, red, green, blue, alpha);
	}
}