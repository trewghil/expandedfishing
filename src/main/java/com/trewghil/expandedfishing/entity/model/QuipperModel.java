package com.trewghil.expandedfishing.entity.model;
// Made with Blockbench 3.6.3
// Exported for Minecraft version 1.12.2 or 1.15.2 (same format for both) for entity models animated with GeckoLib
// Paste this class into your mod and follow the documentation for GeckoLib to use animations. You can find the documentation here: https://github.com/bernie-g/geckolib
// Blockbench plugin created by Gecko

import com.trewghil.expandedfishing.entity.QuipperEntity;
import software.bernie.geckolib.animation.render.AnimatedModelRenderer;
import software.bernie.geckolib.forgetofabric.ResourceLocation;
import software.bernie.geckolib.animation.model.AnimatedEntityModel;

public class QuipperModel extends AnimatedEntityModel<QuipperEntity> {

    private final AnimatedModelRenderer head;
	private final AnimatedModelRenderer body;
	private final AnimatedModelRenderer lower_body;
	private final AnimatedModelRenderer tail;
	private final AnimatedModelRenderer back_fin;
	private final AnimatedModelRenderer bottom_fin;
	private final AnimatedModelRenderer fin_l;
	private final AnimatedModelRenderer fin_r;
	private final AnimatedModelRenderer head_mono;

    public QuipperModel()
    {
        textureWidth = 32;
		textureHeight = 32;
		head = new AnimatedModelRenderer(this);
		head.setRotationPoint(0.5F, 17.5F, 0.0F);
		
		head.setModelRendererName("head");
		this.registerModelRenderer(head);

		body = new AnimatedModelRenderer(this);
		body.setRotationPoint(0.0F, 0.0F, 0.0F);
		head.addChild(body);
		body.setTextureOffset(0, 0).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 4.0F, 4.0F, 0.0F, false);
		body.setModelRendererName("body");
		this.registerModelRenderer(body);

		lower_body = new AnimatedModelRenderer(this);
		lower_body.setRotationPoint(0.0F, 0.5F, 4.0F);
		body.addChild(lower_body);
		lower_body.setTextureOffset(8, 8).addBox(-1.0F, -1.5F, 0.0F, 2.0F, 3.0F, 3.0F, 0.0F, false);
		lower_body.setModelRendererName("lower_body");
		this.registerModelRenderer(lower_body);

		tail = new AnimatedModelRenderer(this);
		tail.setRotationPoint(0.0F, 0.0F, 3.0F);
		lower_body.addChild(tail);
		tail.setTextureOffset(0, 4).addBox(0.0F, -3.5F, 0.0F, 0.0F, 7.0F, 4.0F, 0.0F, false);
		tail.setModelRendererName("tail");
		this.registerModelRenderer(tail);

		back_fin = new AnimatedModelRenderer(this);
		back_fin.setRotationPoint(0.0F, -1.5F, 3.0F);
		body.addChild(back_fin);
		back_fin.setTextureOffset(8, 10).addBox(0.0F, -2.0F, -2.0F, 0.0F, 3.0F, 4.0F, 0.0F, false);
		back_fin.setModelRendererName("back_fin");
		this.registerModelRenderer(back_fin);

		bottom_fin = new AnimatedModelRenderer(this);
		bottom_fin.setRotationPoint(0.0F, 2.0F, 4.0F);
		body.addChild(bottom_fin);
		bottom_fin.setTextureOffset(0, 11).addBox(0.0F, -0.5F, -2.0F, 0.0F, 2.0F, 4.0F, 0.0F, false);
		bottom_fin.setModelRendererName("bottom_fin");
		this.registerModelRenderer(bottom_fin);

		fin_l = new AnimatedModelRenderer(this);
		fin_l.setRotationPoint(1.5F, 1.5F, 1.0F);
		body.addChild(fin_l);
		setRotationAngle(fin_l, 0.0F, 0.0F, -0.7854F);
		fin_l.setTextureOffset(10, 0).addBox(0.0F, 0.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, false);
		fin_l.setModelRendererName("fin_l");
		this.registerModelRenderer(fin_l);

		fin_r = new AnimatedModelRenderer(this);
		fin_r.setRotationPoint(-1.5F, 1.5F, 1.0F);
		body.addChild(fin_r);
		setRotationAngle(fin_r, 0.0F, 0.0F, 0.7854F);
		fin_r.setTextureOffset(0, 0).addBox(0.0F, 0.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, false);
		fin_r.setModelRendererName("fin_r");
		this.registerModelRenderer(fin_r);

		head_mono = new AnimatedModelRenderer(this);
		head_mono.setRotationPoint(0.0F, 0.0F, 0.0F);
		head.addChild(head_mono);
		setRotationAngle(head_mono, -0.5236F, 0.0F, 0.0F);
		head_mono.setTextureOffset(14, 0).addBox(-1.0F, -1.4019F, -1.366F, 2.0F, 3.0F, 2.0F, 0.0F, false);
		head_mono.setModelRendererName("head_mono");
		this.registerModelRenderer(head_mono);

		this.rootBones.add(head);
	}


    @Override
    public ResourceLocation getAnimationFileLocation()
    {
        return new ResourceLocation("expandedfishing", "animations/quipper.json");
    }
}