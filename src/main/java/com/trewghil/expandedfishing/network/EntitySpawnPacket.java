package com.trewghil.expandedfishing.network;

import com.trewghil.expandedfishing.ExpandedFishing;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.PacketContext;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;

import java.util.UUID;

public class EntitySpawnPacket {
    public static final Identifier ID = ExpandedFishing.identify("entity_spawn_packet");

    public static Packet<?> createPacket(Entity entity) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeVarInt(Registry.ENTITY_TYPE.getRawId(entity.getType()));
        buf.writeUuid(entity.getUuid());
        buf.writeVarInt(entity.getEntityId());
        buf.writeDouble(entity.getX());
        buf.writeDouble(entity.getY());
        buf.writeDouble(entity.getZ());
        buf.writeByte(MathHelper.floor(entity.pitch * 256.0F / 360.0F));
        buf.writeByte(MathHelper.floor(entity.yaw * 256.0F / 360.0F));
        if (entity instanceof SpawnDataEntity) {
            ((SpawnDataEntity) entity).writeToBuffer(buf);
        }
        return ServerSidePacketRegistry.INSTANCE.toPacket(ID, buf);
    }

    @Environment(EnvType.CLIENT)
    public static void onPacket(PacketContext context, PacketByteBuf byteBuf) {
        EntityType<?> type = Registry.ENTITY_TYPE.get(byteBuf.readVarInt());
        UUID entityUUID = byteBuf.readUuid();
        int entityID = byteBuf.readVarInt();
        double x = byteBuf.readDouble();
        double y = byteBuf.readDouble();
        double z = byteBuf.readDouble();
        float pitch = (byteBuf.readByte() * 360) / 256.0F;
        float yaw = (byteBuf.readByte() * 360) / 256.0F;
        ClientWorld world = MinecraftClient.getInstance().world;
        Entity entity = type.create(world);
        if (entity instanceof SpawnDataEntity) {
            ((SpawnDataEntity) entity).readFromBuffer(byteBuf);
        }
        context.getTaskQueue().execute(() -> {
            if (entity != null) {
                entity.updatePosition(x, y, z);
                entity.updateTrackedPosition(x, y, z);
                entity.pitch = pitch;
                entity.yaw = yaw;
                entity.setEntityId(entityID);
                entity.setUuid(entityUUID);
                world.addEntity(entityID, entity);
            }
        });
    }
}
