package com.trewghil.expandedfishing.client.render;

import com.trewghil.expandedfishing.ExpandedFishing;
import com.trewghil.expandedfishing.entity.QuipperEntity;
import com.trewghil.expandedfishing.entity.model.QuipperModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class QuipperRenderer extends MobEntityRenderer<QuipperEntity, QuipperModel> {
    private static final Identifier TEXTURE = ExpandedFishing.identify("textures/entity/quipper.png");

    public QuipperRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new QuipperModel(), 0.3F);
    }

    public Identifier getTexture(QuipperEntity quipperEntity) {
        return TEXTURE;
    }

    protected void setupTransforms(QuipperEntity quipperEntity, MatrixStack matrixStack, float f, float g, float h) {
        super.setupTransforms(quipperEntity, matrixStack, f, g, h);
    }
}
