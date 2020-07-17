package com.trewghil.expandedfishing.entity;

import com.trewghil.expandedfishing.ExpandedFishing;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.Registry;

public class ExpandedFishingEntities {

    public ExpandedFishingEntities() {}

    public static final EntityType<SeaUrchinEntity> SEA_URCHIN_ENTITY = FabricEntityTypeBuilder.<SeaUrchinEntity>create(SpawnGroup.MISC, SeaUrchinEntity::new).trackable(74, 2).dimensions(EntityDimensions.fixed(0.25F, 0.25F)).build();

    public static void init() {
        Registry.register(Registry.ENTITY_TYPE, ExpandedFishing.identify("sea_urchin"), SEA_URCHIN_ENTITY);
    }

}
