package com.trewghil.expandedfishing.entity;

import com.trewghil.expandedfishing.ExpandedFishing;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.util.registry.Registry;

public class ExpandedFishingEntities {

    public ExpandedFishingEntities() {}

    public static final EntityType<SeaUrchinEntity> SEA_URCHIN_ENTITY = FabricEntityTypeBuilder.<SeaUrchinEntity>create(SpawnGroup.MISC, SeaUrchinEntity::new).trackable(74, 2).dimensions(EntityDimensions.fixed(0.125F, 0.125F)).build();
    public static final EntityType<QuipperEntity> QUIPPER_ENTITY = FabricEntityTypeBuilder.<QuipperEntity>create(SpawnGroup.WATER_AMBIENT, QuipperEntity::new).dimensions(EntityDimensions.fixed(0.45F, 0.45F)).build();

    public static void init() {
        Registry.register(Registry.ENTITY_TYPE, ExpandedFishing.identify("sea_urchin"), SEA_URCHIN_ENTITY);
        Registry.register(Registry.ENTITY_TYPE, ExpandedFishing.identify("quipper"), QUIPPER_ENTITY);

        FabricDefaultAttributeRegistry.register(QUIPPER_ENTITY, QuipperEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 3.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.9D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1.0D)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 15.0D)
        );

    }

}
