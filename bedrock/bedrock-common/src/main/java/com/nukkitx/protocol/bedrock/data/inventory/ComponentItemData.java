package com.nukkitx.protocol.bedrock.data.inventory;

import com.github.jinahya.bit.io.BitOutput;
import com.nukkitx.nbt.NbtMap;
import com.nukkitx.protocol.serializer.BitDataWritable;
import com.nukkitx.protocol.serializer.BitDataWriter;
import com.nukkitx.protocol.serializer.PacketDataWriter;
import lombok.Value;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public record ComponentItemData(String name, NbtMap data) implements BitDataWritable, PacketDataWriter {
    @Override
    public void write(@NotNull BitOutput output) throws IOException {
        writeString(output, name());
        writeNBTMap(output, data());
    }
}
