package com.trewghil.expandedfishing.client.render;

import com.trewghil.expandedfishing.entity.SeaUrchinEntity;
import com.trewghil.expandedfishing.entity.model.SeaUrchinModel;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class SeaUrchinRenderer extends EntityRenderer<SeaUrchinEntity> {

    private static final Identifier TEXTURE = new Identifier("expandedfishing:textures/entity/sea_urchin.png");
    private final SeaUrchinModel MODEL = new SeaUrchinModel();

    public SeaUrchinRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher);
    }

    @Override
    protected int getBlockLight(SeaUrchinEntity seaUrchinEntity, BlockPos blockPos) {
        return 15;
    }

    @Override
    public void render(SeaUrchinEntity seaUrchinEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {

        matrixStack.push();
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(this.MODEL.getLayer(this.getTexture(seaUrchinEntity)));

        int light = WorldRenderer.getLightmapCoordinates(seaUrchinEntity.getEntityWorld(), seaUrchinEntity.getBlockPos().up());

        MODEL.render(matrixStack, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);

        matrixStack.pop();
        super.render(seaUrchinEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public Identifier getTexture(SeaUrchinEntity seaUrchinEntity) {
        return TEXTURE;
    }
}