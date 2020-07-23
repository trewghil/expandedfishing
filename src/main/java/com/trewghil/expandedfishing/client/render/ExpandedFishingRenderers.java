package com.trewghil.expandedfishing.client.render;

import com.trewghil.expandedfishing.entity.ExpandedFishingEntities;
import com.trewghil.expandedfishing.network.EntitySpawnPacket;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;

public class ExpandedFishingRenderers {

    public static void registerClientboundPackets() {
        ClientSidePacketRegistry.INSTANCE.register(EntitySpawnPacket.ID, EntitySpawnPacket::onPacket);
    }

    public ExpandedFishingRenderers() {}

    public static void init() {
        EntityRendererRegistry.INSTANCE.register(ExpandedFishingEntities.SEA_URCHIN_ENTITY, (entityRenderDispatcher, context) -> new SeaUrchinRenderer(entityRenderDispatcher));
        EntityRendererRegistry.INSTANCE.register(ExpandedFishingEntities.QUIPPER_ENTITY, ((entityRenderDispatcher, context) -> new QuipperRenderer(entityRenderDispatcher)));

        registerClientboundPackets();
    }

}
