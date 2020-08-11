package com.trewghil.expandedfishing.entity.model;// Made with Blockbench 3.6.3
// Exported for Minecraft version 1.12.2 or 1.15.2 (same format for both) for entity models animated with GeckoLib
// Paste this class into your mod and follow the documentation for GeckoLib to use animations. You can find the documentation here: https://github.com/bernie-g/geckolib
// Blockbench plugin created by Gecko

import com.trewghil.expandedfishing.entity.SeaUrchinEntity;
import software.bernie.geckolib.animation.render.AnimatedModelRenderer;
import software.bernie.geckolib.forgetofabric.ResourceLocation;
import software.bernie.geckolib.animation.model.AnimatedEntityModel;

public class SeaUrchinModel extends AnimatedEntityModel<SeaUrchinEntity> {

    private final AnimatedModelRenderer urchin;
	private final AnimatedModelRenderer spines;
	private final AnimatedModelRenderer plus;
	private final AnimatedModelRenderer diagonal;

    public SeaUrchinModel()
    {
        textureWidth = 16;
		textureHeight = 16;
		urchin = new AnimatedModelRenderer(this);
		urchin.setRotationPoint(0.0F, 24.0F, 0.0F);
		urchin.setTextureOffset(8, 0).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
		urchin.setModelRendererName("urchin");
		this.registerModelRenderer(urchin);

		spines = new AnimatedModelRenderer(this);
		spines.setRotationPoint(0.0F, 0.0F, 0.0F);
		urchin.addChild(spines);
		
		spines.setModelRendererName("spines");
		this.registerModelRenderer(spines);

		plus = new AnimatedModelRenderer(this);
		plus.setRotationPoint(0.0F, 0.0F, 0.0F);
		spines.addChild(plus);
		plus.setTextureOffset(0, 4).addBox(0.0F, -2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 0.0F, false);
		plus.setTextureOffset(8, 8).addBox(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 0.0F, 0.0F, false);
		plus.setModelRendererName("plus");
		this.registerModelRenderer(plus);

		diagonal = new AnimatedModelRenderer(this);
		diagonal.setRotationPoint(0.0F, 0.0F, 0.0F);
		spines.addChild(diagonal);
		setRotationAngle(diagonal, 0.0F, -0.7854F, 0.0F);
		diagonal.setTextureOffset(0, 0).addBox(0.0F, -2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 0.0F, false);
		diagonal.setTextureOffset(0, 0).addBox(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 0.0F, 0.0F, false);
		diagonal.setModelRendererName("diagonal");
		this.registerModelRenderer(diagonal);

		this.rootBones.add(urchin);
	}


    @Override
    public ResourceLocation getAnimationFileLocation()
    {
        return new ResourceLocation("expandedfishing", "animations/sea_urchin.json");
    }
}