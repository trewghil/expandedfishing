package com.trewghil.expandedfishing.client.render;

import com.trewghil.expandedfishing.ExpandedFishing;
import com.trewghil.expandedfishing.entity.QuipperEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.CodEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class QuipperRenderer extends MobEntityRenderer<QuipperEntity, CodEntityModel<QuipperEntity>> {
    private static final Identifier TEXTURE = ExpandedFishing.identify("textures/entity/quipper.png");

    public QuipperRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new CodEntityModel(), 0.3F);
    }

    public Identifier getTexture(QuipperEntity quipperEntity) {
        return TEXTURE;
    }

    protected void setupTransforms(QuipperEntity quipperEntity, MatrixStack matrixStack, float f, float g, float h) {
        super.setupTransforms(quipperEntity, matrixStack, f, g, h);
        float i = 4.3F * MathHelper.sin(0.6F * f);
        matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(i));
        if(quipperEntity.hasTarget()) {

        }
        //if (!quipperEntity.isTouchingWater()) {
        //    matrixStack.translate(0.10000000149011612D, 0.10000000149011612D, -0.10000000149011612D);
        //    matrixStack.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(90.0F));
        //}

    }
}
