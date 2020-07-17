package com.trewghil.expandedfishing.network;

import net.minecraft.network.PacketByteBuf;

public interface SpawnDataEntity {
    void writeToBuffer(PacketByteBuf buffer);

    void readFromBuffer(PacketByteBuf buffer);
}
